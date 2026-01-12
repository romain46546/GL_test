package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl24
 * @date 01/01/2026
 */
public class Lower extends AbstractOpIneq {

    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<";
    }

    @Override
    protected void codeGenExp(DecacCompiler compiler, int currentRegister) {
        int max = compiler.getCompilerOptions().getRegisters() - 1;

        if (currentRegister == max) {
            // Spill registre
            getLeftOperand().codeGenExp(compiler, currentRegister);
            compiler.addInstruction(new PUSH(Register.getR(currentRegister)));

            getRightOperand().codeGenExp(compiler, currentRegister);
            compiler.addInstruction(new LOAD(Register.getR(currentRegister), Register.R0));

            compiler.addInstruction(new POP(Register.getR(currentRegister)));
            compiler.addInstruction(new CMP(Register.R0, Register.getR(currentRegister)));
            compiler.addInstruction(new SLT(Register.getR(currentRegister)));

        } else {
            getLeftOperand().codeGenExp(compiler, currentRegister);
            getRightOperand().codeGenExp(compiler, currentRegister + 1);

            compiler.addInstruction(new CMP(
                    Register.getR(currentRegister + 1),
                    Register.getR(currentRegister)
            ));
            compiler.addInstruction(new SLT(Register.getR(currentRegister)));
        }
    }


}
