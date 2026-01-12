package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * @author gl24
 * @date 01/01/2026
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "*";
    }

    @Override
    protected void codeGenOpArith(DecacCompiler compiler, int currentRegister) {
        int max = compiler.getCompilerOptions().getRegisters() - 1;
        if (max == currentRegister){
            compiler.addInstruction(new MUL(Register.R0, Register.getR(currentRegister)));
        } else {
            compiler.addInstruction(new MUL(Register.getR(currentRegister+1), Register.getR(currentRegister)));
        }
        if (getType().isFloat()){
            compiler.addInstruction(new BOV(new Label("overflow_error")));
        }
    }
}
