package indi.wh0wfg.mlox.language.psi;

import indi.wh0wfg.mlox.language.psi.impl.*;

public interface MloxElementTypes {
    // statements
    MloxElementType FUNCTION_STMT = new MloxElementType("FUNCTION_STMT", MloxFunctionStmtPsiElement::new);
    MloxElementType CLASS_STMT = new MloxElementType("CLASS_STMT", MloxClassStmtPsiElement::new);
    MloxElementType FOR_STMT = new MloxElementType("FOR_STMT", MloxForStmtPsiElement::new);
    MloxElementType PRINT_STMT = new MloxElementType("PRINT_STMT", MloxPrintStmtPsiElement::new);
    MloxElementType IF_STMT = new MloxElementType("IF_STMT", MloxIfStmtPsiElement::new);
    MloxElementType EXPRESSION_STMT = new MloxElementType("EXPRESSION_STMT", MloxExpressionStmtPsiElement::new);
    MloxElementType BLOCK_STMT = new MloxElementType("BLOCK_STMT", MloxBlockStmtPsiElement::new);
    MloxElementType RETURN_STMT = new MloxElementType("RETURN_STMT", MloxReturnStmtPsiElement::new);
    MloxElementType VAR_STMT = new MloxElementType("VAR_STMT", MloxVariableExprPsiElement::new);
    MloxElementType WHILE_STMT = new MloxElementType("WHILE_STMT", MloxWhileStmtPsiElement::new);


    // expressions
    MloxElementType LITERAL_EXPR = new MloxElementType("LITERAL_EXPR", MloxLiteralExprPsiElement::new);
    MloxElementType UNARY_EXPR = new MloxElementType("UNARY_EXPR", MloxUnaryExprPsiElement::new);
    MloxElementType BINARY_EXPR = new MloxElementType("BINARY_EXPR", MloxBinaryExprPsiElement::new);
    MloxElementType LOGIC_EXPR = new MloxElementType("LOGIC_EXPR", MloxLogicExprPsiElement::new);
    MloxElementType VARIABLE_EXPR = new MloxElementType("VARIABLE_EXPR", MloxVariableExprPsiElement::new);
    MloxElementType ASSIGNMENT_EXPR = new MloxElementType("ASSIGNMENT_EXPR", MloxAssignmentExprPsiElement::new);
    MloxElementType CALL_EXPR = new MloxElementType("CALL_EXPR", MloxCallExprPsiElement::new);
    MloxElementType GET_EXPR = new MloxElementType("GET_EXPR", MloxGetExprPsiElement::new);
    MloxElementType SUPER_EXPR = new MloxElementType("SUPER_EXPR", MloxSuperExprPsiElement::new);
    MloxElementType THIS_EXPR = new MloxElementType("THIS_EXPR", MloxThisExprPsiElement::new);
    MloxElementType GROUPING_EXPR = new MloxElementType("GROUPING_EXPR", MloxGroupingExprPsiElement::new);
    MloxElementType EXPRESSION = new MloxElementType("EXPRESSION");
}
