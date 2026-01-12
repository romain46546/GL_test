package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SNE;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.POP;

public class NotEquals extends AbstractOpExactCmp {

    public NotEquals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "!=";
    }

    @Override
    protected void codeGenExp(DecacCompiler compiler, int currentRegister) {
        int max = compiler.getCompilerOptions().getRegisters() - 1;

        if (currentRegister == max) {
            // Cas débordement de registres (comme Multiply)

            getLeftOperand().codeGenExp(compiler, currentRegister);
            compiler.addInstruction(new PUSH(Register.getR(currentRegister)));

            getRightOperand().codeGenExp(compiler, currentRegister);
            compiler.addInstruction(new LOAD(Register.getR(currentRegister), Register.R0));

            compiler.addInstruction(new POP(Register.getR(currentRegister)));

            compiler.addInstruction(new CMP(Register.R0, Register.getR(currentRegister)));
            compiler.addInstruction(new SNE(Register.getR(currentRegister)));

        } else {
            // Cas normal

            getLeftOperand().codeGenExp(compiler, currentRegister);
            getRightOperand().codeGenExp(compiler, currentRegister + 1);

            compiler.addInstruction(new CMP(
                    Register.getR(currentRegister + 1),
                    Register.getR(currentRegister)
            ));
            compiler.addInstruction(new SNE(Register.getR(currentRegister)));
        }
    }
}
