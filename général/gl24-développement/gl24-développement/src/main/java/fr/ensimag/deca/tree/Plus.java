package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * @author gl24
 * @date 01/01/2026
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }

    @Override
    protected void codeGenOpArith(DecacCompiler compiler, int currentRegister) {
        int max = compiler.getCompilerOptions().getRegisters() - 1;
        if (max == currentRegister){
            compiler.addInstruction(new ADD(Register.R0, Register.getR(currentRegister)));
        } else {
            compiler.addInstruction(new ADD(Register.getR(currentRegister+1), Register.getR(currentRegister)));
        }
        if (getType().isFloat()){
            compiler.addInstruction(new BOV(new Label("overflow_error")));
        }
    }
}
