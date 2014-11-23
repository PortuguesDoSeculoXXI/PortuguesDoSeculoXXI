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
    private List<String> QTableList = new ArrayList<>();
    private List<QField> QFieldList = new ArrayList<>();
    private List<QSelectField> QSelectFieldList = new ArrayList<>();
    private List<QWhereField> QWhereFieldList = new ArrayList<>();
    private List<QWhereField> QJoinFieldList = new ArrayList<>();
    
    /**
     * To add a table to the query.
     *
     * @param tableName name o the table to be added.
     */
    public boolean addTable(String tableName) {
        if (!QTableList.contains(tableName)) {
            QTableList.add(tableName);
            return true;
        } else {
            return false;
        }
    }

    /**
     * To add a field for query.
     * 
     * @param fieldName
     * @param value
     * @param dataType data type of the field (Specify it as "text" for string
     * types , "num" for numerical types)
     */
    private void addField(String fieldName, String value, String dataType) {

        if (dataType.equals("text")) {

            boolean isWildchar = (StringUtilities.indexOfFirstContainedCharacter(value, "'") != -1);
            value = getInsertValueLeftChar(isWildchar) + value + getInsertValueRightChar(isWildchar);
        }

        QField field = new QField(fieldName, value, dataType);

        QFieldList.add(field);
    }

    /**
     * To add a field for Select query.
     *
     * @param fieldName Field name to be added.
     * @param aliasName Alias name for the select field.
     */
    public void addSelectField(String fieldName, String aliasName) {

        if (StringUtilities.indexOfFirstContainedCharacter(fieldName, " /-") != -1) {
            fieldName = "`" + fieldName + "`";
        }

        if (StringUtilities.indexOfFirstContainedCharacter(aliasName, " /-") != -1) {
            aliasName = "`" + aliasName + "`";
        }

        QSelectField field = new QSelectField(fieldName, aliasName);

        QSelectFieldList.add(field);
    }

    /**
     * Overloaded function To add a field for Select query.
     *
     * @param fieldName Field name to be added.
     */
    public void addSelectField(String fieldName) {

        if (StringUtilities.indexOfFirstContainedCharacter(fieldName, " /-") != -1) {
            fieldName = "`" + fieldName + "`";
        }
        QSelectField field = new QSelectField(fieldName);

        QSelectFieldList.add(field);
    }

    /**
     * To add where condition in a query.
     *
     * @param fieldName Field name for the condition.
     * @param val Value for the field to be compared.
     * @param op Operator('=', '<', '>' etc...for the condition)
     */
    public void addWhereField(String fieldName, String val, String op) {

        if (StringUtilities.indexOfFirstContainedCharacter(fieldName, " /-") != -1) {
            fieldName = "`" + fieldName + "`";
        }
        QWhereField field = new QWhereField(fieldName, val, op, "AND", false);

        QWhereFieldList.add(field);
    }

    /**
     * To add where condition in a query.
     *
     * @param fieldName Field name for the condition.
     * @param val Value for the field to be compared.
     * @param op Operator('=', '<', '>' etc...for the condition)
     * @param concat Operator for the where field ('AND' or 'OR')
     */
    public void addWhereField(String fieldName, String val, String op, String concat) {

        if (StringUtilities.indexOfFirstContainedCharacter(fieldName, " /-") != -1) {
            fieldName = "`" + fieldName + "`";
        }
        QWhereField field = new QWhereField(fieldName, val, op, concat, false);

        QWhereFieldList.add(field);
    }

    /**
     * To add where condition in a query.
     *
     * @param fieldName Field name for the condition.
     * @param val Value for the field to be compared.
     */
    public void addWhereField(String fieldName, String val) {

        if (StringUtilities.indexOfFirstContainedCharacter(fieldName, " /-") != -1) {
            fieldName = "`" + fieldName + "`";
        }
        QWhereField field = new QWhereField(fieldName, val, "=", "AND", false);

        QWhereFieldList.add(field);
    }

    /**
     * To add a sub query for condition in a query.
     *
     * @param fieldName Field name for the condition.
     * @param val Value for the field to be compared.
     */
    public void addWhereSubquery(String fieldName, String subQuery) {

        if (StringUtilities.indexOfFirstContainedCharacter(fieldName, " /-") != -1) {
            fieldName = "`" + fieldName + "`";
        }
        QWhereField field = new QWhereField(fieldName, subQuery, "=", "AND", true);

        QWhereFieldList.add(field);
    }

    /**
     * To add a join in a query.
     *
     * @param leftFielield Field on left.
     * @param rightField Field on right.
     * @param op Operator('=', '<', '>' etc...for the condition)
     */
    public void addJoinField(String leftFielield, String rightField, String op) {

        if (StringUtilities.indexOfFirstContainedCharacter(leftFielield, " /-") != -1) {
            leftFielield = "`" + leftFielield + "`";
        }

        if (StringUtilities.indexOfFirstContainedCharacter(rightField, " /-") != -1) {
            leftFielield = "`" + rightField + "`";
        }

        QWhereField field = new QWhereField(leftFielield, rightField, op, "AND", false);

        QJoinFieldList.add(field);
    }

    /**
     * Overloaded function To add to join query, here default operator is '='.
     *
     * @param fieldName1 Left field
     * @param fieldName2 Right field
     */
    public void addJoinField(String leftField, String rightField) {

        if (StringUtilities.indexOfFirstContainedCharacter(leftField, " /-") != -1) {
            leftField = "`" + leftField + "`";
        }
        QWhereField field = new QWhereField(leftField, rightField, "=", "AND", false);

        QJoinFieldList.add(field);
    }

    /**
     * To get the insert query.
     *
     * @returns Insert query.
     */
    public String getInsertQuery() {
        String sQuery = "INSERT INTO ";

        sQuery += QTableList.get(0) + " ( ";

        boolean bFlag = false;
        String sField;

        Iterator<QField> qFieldListItr = QFieldList.iterator();

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

        sQuery += ") VALUES(";
        bFlag = false;

        qFieldListItr = QFieldList.iterator();

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
        String sQuery = "SELECT ";
        String sField;
        boolean bFlag = false;
        
        Iterator<QSelectField> qSelectFieldListItr = QSelectFieldList.iterator();
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

        sQuery += " FROM ";
        bFlag = false;
        Iterator<String> qTableListItr = QTableList.iterator();
        while (qTableListItr.hasNext()) {
            String tableName = qTableListItr.next();
            if (bFlag) {
                sQuery += ", ";
            }
            sQuery += tableName;
            bFlag = true;
        }

        if (!QWhereFieldList.isEmpty() || (!QJoinFieldList.isEmpty())) {
            sQuery += " WHERE ";
        }

        bFlag = false;
        Iterator<QWhereField> qWhereFieldListItr = QWhereFieldList.iterator();

        while (qWhereFieldListItr.hasNext()) {
            QWhereField whereField = qWhereFieldListItr.next();
            if (bFlag) {
                sQuery += " " + whereField.concat + " ";
            }
            sQuery += whereField.fieldName + " " + whereField.operand + " " + getWhereFieldLeftSeperatorChar(whereField.isSubQuery)
                    + whereField.value + getWhereFieldRightSeperatorChar(whereField.isSubQuery);
            bFlag = true;
        }

        if (!QJoinFieldList.isEmpty() && !QWhereFieldList.isEmpty()) {
            sQuery += " AND ";
        }

        bFlag = false;
        Iterator<QWhereField> qJoinFieldListItr = QJoinFieldList.iterator();
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

        String sQuery = "DELETE ";
        sQuery += " FROM ";
        boolean bFlag = false;
        Iterator<String> qTableListItr = QTableList.iterator();
        while (qTableListItr.hasNext()) {
            String tableName = qTableListItr.next();
            if (bFlag) {
                sQuery += ", ";
            }
            sQuery += tableName;
            bFlag = true;
        }

        if (!QWhereFieldList.isEmpty()) {
            sQuery += " WHERE ";
        }

        bFlag = false;
        Iterator<QWhereField> qWhereFieldListItr = QWhereFieldList.iterator();
        while (qWhereFieldListItr.hasNext()) {
            QWhereField whereField = qWhereFieldListItr.next();
            if (bFlag) {
                sQuery += " " + whereField.concat + " ";
            }
            sQuery += whereField.fieldName + " " + whereField.operand + " '" + whereField.value + "'";
            bFlag = true;
        }

        if (!QJoinFieldList.isEmpty()) {
            sQuery += " WHERE ";
        }

        bFlag = false;
        Iterator<QWhereField> qJoinFieldListItr = QJoinFieldList.iterator();
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
     * To clear the query generator .
     *
     * @returns
     */
    public void clear() {
        QTableList.clear();
        QFieldList.clear();
        QSelectFieldList.clear();
        QWhereFieldList.clear();
        QJoinFieldList.clear();
    }

    protected String getWhereFieldLeftSeperatorChar(boolean isSubQuery) {
        return (isSubQuery ? "(" : "\"");
    }

    protected String getWhereFieldRightSeperatorChar(boolean isSubQuery) {
        return (isSubQuery ? ")" : "\"");
    }

    protected String getInsertValueLeftChar(boolean isWildChar) {
        return (isWildChar ? "\"" : "'");
    }

    protected String getInsertValueRightChar(boolean isWildChar) {
        return (isWildChar ? "\"" : "'");
    }

    protected class QField {

        public String fieldName = new String();
        public String value = new String();
        public String dataType = new String();

        public QField() {
            fieldName = "";
            value = "";
            dataType = "";
        }

        public QField(String fieldName, String value, String dataType) {
            this.fieldName = fieldName;
            this.value = value;
            this.dataType = dataType;

        }
    };

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
