package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl24
 * @date 01/01/2026
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

    @Override
    protected void codeGenOpArith(DecacCompiler compiler, int currentRegister) {
        int max = compiler.getCompilerOptions().getRegisters() - 1;
        if (max == currentRegister){
            if (getType().isInt()){
                compiler.addInstruction(new CMP(new ImmediateInteger(0), Register.getR(currentRegister)));
                compiler.addInstruction(new BEQ(new Label("zero_division_error")));
                compiler.addInstruction(new QUO(Register.R0, Register.getR(currentRegister)));
            }
            else {
                compiler.addInstruction(new CMP(new ImmediateFloat(0), Register.getR(currentRegister)));
                compiler.addInstruction(new BEQ(new Label("zero_division_error")));
                compiler.addInstruction(new DIV(Register.R0, Register.getR(currentRegister)));
            }
        } else {
            if (getType().isInt()) {
                compiler.addInstruction(new QUO(Register.getR(currentRegister + 1), Register.getR(currentRegister)));
            } else {
                compiler.addInstruction(new DIV(Register.getR(currentRegister + 1), Register.getR(currentRegister)));
            }
            compiler.addInstruction(new BOV(new Label("zero_division_error")));

        }
    }
}
