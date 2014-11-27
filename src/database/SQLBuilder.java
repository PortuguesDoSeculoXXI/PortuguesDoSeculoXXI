package database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * SQL Query Builder.
 * This class creates SQL queries dynamically.
 * 
 * To do:
 *  - Inner join and left outer join
 *  - Where clause with LIKE
 *  - Where clause with num ou text
 * 
 * @author PTXXI
 */
class SQLBuilder {
    
    private static class StringUtilities
    {
        /**
         * Utility function to check a string contains a set of characters.
         * @param str
         * @param token
         * @return
         */
        private static int indexOfFirstContainedCharacter(String str, String token) {
            Set<Character> set = new HashSet<>();
            for (int i=0; i<token.length(); i++) {
                set.add(token.charAt(i)); // Build a constant-time lookup table.
            }
            for (int i=0; i<str.length(); i++) {
                if (set.contains(str.charAt(i))) {
                    return i; // Found a character in s1 also in s2.
                }
            }
            return -1; // No matches.
        }
    }
    
    /**
     * Lists for each SQL statement.
     */
    private List<String> qTableList = new ArrayList<>();
    private List<QField> qFieldList = new ArrayList<>();
    private List<QSelectField> qSelectFieldList = new ArrayList<>();
    private List<QWhereField> qWhereFieldList = new ArrayList<>();
    private List<QWhereField> qJoinFieldList = new ArrayList<>();
    
    /**
     * Defining data type of field.
     */    
    public enum DataType {
        asText,
        asNum
    }
    
    /**
     * To add a table to the query.
     *
     * @param tableName name o the table to be added.
     */
    public SQLBuilder addTable(String tableName) {
        if (!qTableList.contains(tableName)) {
            qTableList.add(tableName);
        }
        return this;
    }
    
    /**
     * Check if has regular chars for the query.
     * 
     * @param str
     * @returns normalized string for query
     */    
    private String checkValidQueryString(String str) {
        if (StringUtilities.indexOfFirstContainedCharacter(str, " /-.,") != -1) {
            return "'" + str + "'";
        }
        return str;
    }

    /**
     * To add a field for query.
     * 
     * @param fieldName
     * @param value
     * @param dataType data type of the field (Specify it as "text" for string
     * types , "num" for numerical types).
     * @returns SQLBuilder instance.
     */
    public SQLBuilder addField(String fieldName, String value, DataType dataType) {
        // Check data type
        if (dataType == DataType.asText) {
            boolean isWildchar = (StringUtilities.indexOfFirstContainedCharacter(value, "'") != -1);
            value = getInsertValueLeftChar(isWildchar) + value + getInsertValueRightChar(isWildchar);
        }

        QField field = new QField(fieldName, value, dataType);
        qFieldList.add(field);
        return this;
    }

    /**
     * To add a field for Select query.
     *
     * @param fieldName Field name to be added.
     * @param aliasName Alias name for the select field.
     * @returns SQLBuilder instance.
     */
    public SQLBuilder selectField(String fieldName, String aliasName) {
        fieldName = checkValidQueryString(fieldName);
        aliasName = checkValidQueryString(aliasName);
        // Add field in the query
        QSelectField field = new QSelectField(fieldName, aliasName);
        qSelectFieldList.add(field);
        return this;
    }

    /**
     * Overloaded function To add a field for Select query.
     *
     * @param fieldName Field name to be added.
     * @returns SQLBuilder instance.
     */
    public SQLBuilder selectField(String fieldName) {
        fieldName = checkValidQueryString(fieldName);
        // Add field in the query
        QSelectField field = new QSelectField(fieldName);
        qSelectFieldList.add(field);
        return this;
    }

    /**
     * To add where condition in a query.
     *
     * @param fieldName Field name for the condition.
     * @param val Value for the field to be compared.
     * @param op Operator('=', '<', '>' etc... for the condition).
     * @returns SQLBuilder instance.
     */
    public SQLBuilder whereField(String fieldName, String val, String op) {
        fieldName = checkValidQueryString(fieldName);
        // Add field in the query
        QWhereField field = new QWhereField(fieldName, val, op, "AND", false);
        qWhereFieldList.add(field);
        return this;
    }

    /**
     * To add where condition in a query.
     *
     * @param fieldName Field name for the condition.
     * @param val Value for the field to be compared.
     * @param op Operator('=', '<', '>' etc...for the condition).
     * @param concat Operator for the where field ('AND' or 'OR').
     * @returns SQLBuilder instance.
     */
    public SQLBuilder whereField(String fieldName, String val, String op, String concat) {
        fieldName = checkValidQueryString(fieldName);
        // Add field in the query
        QWhereField field = new QWhereField(fieldName, val, op, concat, false);
        qWhereFieldList.add(field);
        return this;
    }

    /**
     * To add where condition in a query.
     *
     * @param fieldName Field name for the condition.
     * @param val Value for the field to be compared.
     * @returns SQLBuilder instance.
     */
    public SQLBuilder whereField(String fieldName, String val) {
        fieldName = checkValidQueryString(fieldName);
        // Add in the query
        QWhereField field = new QWhereField(fieldName, val, "=", "AND", false);
        qWhereFieldList.add(field);
        return this;
    }

    /**
     * To add a sub query for condition in a query.
     *
     * @param fieldName Field name for the condition.
     * @param val Value for the field to be compared.
     * @returns SQLBuilder instance.
     */
    public SQLBuilder whereSubquery(String fieldName, String subQuery) {
        fieldName = checkValidQueryString(fieldName);
        // Add in the query
        QWhereField field = new QWhereField(fieldName, subQuery, "=", "AND", true);
        qWhereFieldList.add(field);
        return this;
    }

    /**
     * To get the insert query.
     *
     * @returns Insert query.
     */
    public String getInsertQuery() {
        if (qTableList.isEmpty())
            return "";
        
        String sQuery = "INSERT INTO ";

        sQuery += qTableList.get(0) + " (";

        boolean bFlag = false;
        String sField;

        Iterator<QField> qFieldListItr = qFieldList.iterator();
        while (qFieldListItr.hasNext()) {
            sField = "";
            QField field = qFieldListItr.next();
            if (bFlag) {
                sField += ",";
            }
            sField += field.fieldName;
            sQuery += sField;
            bFlag = true;
        }

        sQuery += ") VALUES (";
        bFlag = false;

        qFieldListItr = qFieldList.iterator();
        while (qFieldListItr.hasNext()) {
            sField = "";
            QField field = qFieldListItr.next();
            if (bFlag) {
                sField += ",";
            }
            
            sField += field.value;
            sQuery += sField;
            bFlag = true;
        }

        sQuery += (")");
        return sQuery;
    }

    /**
     * To get the select query.
     *
     * @returns select query.
     */
    public String getSelectQuery() {
        if (qTableList.isEmpty())
            return "";
        
        String sQuery = "SELECT ";
        String sField;
        boolean bFlag = false;
                
        if (qSelectFieldList.isEmpty()) {
            sQuery += "*";
        }
        else {
            Iterator<QSelectField> qSelectFieldListItr = qSelectFieldList.iterator();
            while (qSelectFieldListItr.hasNext()) {
                QSelectField selectField = qSelectFieldListItr.next();
                sField = "";
                if (bFlag) {
                    sField += ", ";
                }
                sField += selectField.fieldName;
                if (!selectField.alias.equals("")) {
                    sField += " AS " + selectField.alias;
                }

                sQuery += sField;
                bFlag = true;
            }
        }
        
        sQuery += " FROM ";
        bFlag = false;
        Iterator<String> qTableListItr = qTableList.iterator();
        while (qTableListItr.hasNext()) {
            String tableName = qTableListItr.next();
            if (bFlag) {
                sQuery += ", ";
            }
            sQuery += tableName;
            bFlag = true;
        }

        if (!qWhereFieldList.isEmpty() || (!qJoinFieldList.isEmpty())) {
            sQuery += " WHERE ";
        }

        bFlag = false;
        Iterator<QWhereField> qWhereFieldListItr = qWhereFieldList.iterator();

        while (qWhereFieldListItr.hasNext()) {
            QWhereField whereField = qWhereFieldListItr.next();
            if (bFlag) {
                sQuery += " " + whereField.concat + " ";
            }
            sQuery += whereField.fieldName + " " + whereField.operand + " " + getWhereFieldLeftSeperatorChar(whereField.isSubQuery)
                    + whereField.value + getWhereFieldRightSeperatorChar(whereField.isSubQuery);
            bFlag = true;
        }

        if (!qJoinFieldList.isEmpty() && !qWhereFieldList.isEmpty()) {
            sQuery += " AND ";
        }

        bFlag = false;
        Iterator<QWhereField> qJoinFieldListItr = qJoinFieldList.iterator();
        while (qJoinFieldListItr.hasNext()) {
            QWhereField joinField = qJoinFieldListItr.next();
            if (bFlag) {
                sQuery += " " + joinField.concat + " ";
            }
            sQuery += joinField.fieldName + " " + joinField.operand + joinField.value;
            bFlag = true;
        }

        return sQuery;
    }

    /**
     * To get the delete query.
     *
     * @returns Delete query as String.
     */
    public String getDeleteQuery() {
        if (qTableList.isEmpty())
            return "";

        String sQuery = "DELETE FROM ";
        boolean bFlag = false;
        Iterator<String> qTableListItr = qTableList.iterator();
        while (qTableListItr.hasNext()) {
            String tableName = qTableListItr.next();
            if (bFlag) {
                sQuery += ", ";
            }
            sQuery += tableName;
            bFlag = true;
        }

        if (!qWhereFieldList.isEmpty()) {
            sQuery += " WHERE ";
        }

        bFlag = false;
        Iterator<QWhereField> qWhereFieldListItr = qWhereFieldList.iterator();
        while (qWhereFieldListItr.hasNext()) {
            QWhereField whereField = qWhereFieldListItr.next();
            if (bFlag) {
                sQuery += " " + whereField.concat + " ";
            }
            sQuery += whereField.fieldName + " " + whereField.operand + " '" + whereField.value + "'";
            bFlag = true;
        }

        if (!qJoinFieldList.isEmpty()) {
            sQuery += " WHERE ";
        }

        bFlag = false;
        Iterator<QWhereField> qJoinFieldListItr = qJoinFieldList.iterator();
        while (qJoinFieldListItr.hasNext()) {
            QWhereField joinField = qJoinFieldListItr.next();
            if (bFlag) {
                sQuery += " " + joinField.concat + " ";
            }
            sQuery += joinField.fieldName + " " + joinField.operand + " '" + joinField.value + "'";
            bFlag = true;
        }

        return sQuery;
    }

    /**
     * To clear the query generator.
     *
     */
    public void clear() {
        qTableList.clear();
        qFieldList.clear();
        qSelectFieldList.clear();
        qWhereFieldList.clear();
        qJoinFieldList.clear();
    }

    private String getWhereFieldLeftSeperatorChar(boolean isSubQuery) {
        return (isSubQuery ? "(" : "\"");
    }

    private String getWhereFieldRightSeperatorChar(boolean isSubQuery) {
        return (isSubQuery ? ")" : "\"");
    }

    private String getInsertValueLeftChar(boolean isWildChar) {
        return (isWildChar ? "\"" : "'");
    }

    private String getInsertValueRightChar(boolean isWildChar) {
        return (isWildChar ? "\"" : "'");
    }

    /**
     * QField class to represent a Query field.
     */
    protected class QField {
        
        public String fieldName = new String();
        public String value = new String();
        public DataType dataType;

        public QField() {
            fieldName = "";
            value = "";
            dataType = DataType.asText; //Default
        }

        public QField(String fieldName, String value, DataType dataType) {
            this.fieldName = fieldName;
            this.value = value;
            this.dataType = dataType;
        }
        
    };

    /**
     * QWhereField class to represent a Where field.
     */
    protected class QWhereField {

        public String fieldName = new String();
        public String value = new String();
        public String operand = new String();
        public String concat = new String();
        public boolean isSubQuery = false;

        public QWhereField() {
            fieldName = "";
            value = "";
            operand = "";
            concat = "";
            isSubQuery = false;
        }

        public QWhereField(String fieldName, String value, String operand, String concat, boolean isSubQuery) {
            this.fieldName = fieldName;
            this.value = value;
            this.operand = operand;
            this.concat = concat;
            this.isSubQuery = isSubQuery;
        }
    };
    
    /**
     * QSelectField class to represent a Select field.
     */
    protected class QSelectField {

        public String fieldName = new String();
        public String alias = new String();

        public QSelectField() {
            fieldName = "";
            alias = "";
        }

        public QSelectField(String fieldName) {
            this.fieldName = fieldName;
            alias = "";
        }

        public QSelectField(String fieldName, String alias) {
            this.fieldName = fieldName;
            this.alias = alias;

        }
    };
}
