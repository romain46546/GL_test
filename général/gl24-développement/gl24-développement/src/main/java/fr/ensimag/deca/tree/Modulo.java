package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.REM;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 *
 * @author gl24
 * @date 01/01/2026
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type leftType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        if (!leftType.isInt() || !rightType.isInt()) {
            throw new ContextualError("Modulo operands must be integers", getLocation());
        }
        setType(compiler.environmentType.INT);
        return compiler.environmentType.INT;
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }

    @Override
    protected void codeGenOpArith(DecacCompiler compiler, int currentRegister) {
        int max = compiler.getCompilerOptions().getRegisters() - 1;
        if (max == currentRegister){
            compiler.addInstruction(new REM(Register.R0, Register.getR(currentRegister)));
        } else {
            compiler.addInstruction(new REM(Register.getR(currentRegister+1), Register.getR(currentRegister)));
        }
        if (getType().isFloat()){
            compiler.addInstruction(new BOV(new Label("overflow_error")));
        }
    }
}
