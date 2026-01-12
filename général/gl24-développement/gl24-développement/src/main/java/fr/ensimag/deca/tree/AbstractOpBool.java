package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 *
 * @author gl24
 * @date 01/01/2026
 */
public abstract class AbstractOpBool extends AbstractBinaryExpr {

    public AbstractOpBool(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type leftType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        if (!leftType.isBoolean() || !rightType.isBoolean()) {
            throw new ContextualError("Boolean operation with non-boolean operands", getLocation());
        }
        setType(compiler.environmentType.BOOLEAN);
        return compiler.environmentType.BOOLEAN;
    }

    @Override
    protected void codeGenExp(DecacCompiler compiler, int currentRegister) {
        // charger les deux opérandes dans R[currentRegister] et R[currentRegister+1]
        getLeftOperand().codeGenExp(compiler, currentRegister);
        getRightOperand().codeGenExp(compiler, currentRegister + 1);

        // R[currentRegister] = R[currentRegister] && R[currentRegister+1]
        compiler.addInstruction(new MUL(Register.getR(currentRegister), Register.getR(currentRegister + 1)));
        compiler.addInstruction(new LOAD(Register.getR(currentRegister + 1), Register.getR(currentRegister)));
    }


}
