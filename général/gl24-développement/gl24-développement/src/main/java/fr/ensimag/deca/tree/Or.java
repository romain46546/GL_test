package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;

/**
 *
 * @author gl24
 * @date 01/01/2026
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }

    @Override
    protected void codeGenExp(DecacCompiler compiler, int currentRegister) {
        AbstractExpr sum = new Plus(getLeftOperand(), getRightOperand());
        sum.setType(compiler.environmentType.BOOLEAN);
        AbstractExpr orExpr = new NotEquals(sum, new IntLiteral(0));
        orExpr.codeGenExp(compiler, currentRegister);
    }

}
