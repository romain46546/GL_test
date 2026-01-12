package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 *
 * @author gl24
 * @date 01/01/2026
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    @Override
    protected void codeGenExp(DecacCompiler compiler, int currentRegister) {
        AbstractExpr multiply = new Multiply(getLeftOperand(), getRightOperand());
        multiply.setType(compiler.environmentType.BOOLEAN);
        multiply.codeGenExp(compiler, currentRegister);
    }


}
