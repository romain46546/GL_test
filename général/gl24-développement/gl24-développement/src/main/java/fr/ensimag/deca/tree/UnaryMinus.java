package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 * @author gl24
 * @date 01/01/2026
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = getOperand().verifyExpr(compiler, localEnv, currentClass);
        if (!type.isInt() && !type.isFloat()) {
            throw new ContextualError("Unary minus on non-numeric expression", getLocation());
        }
        setType(type);
        return type;
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

    @Override
    protected void codeGenExp(DecacCompiler compiler, int currentRegister) {
        getOperand().codeGenExp(compiler, currentRegister);
        if (getOperand().getType().isInt()){
            compiler.addInstruction(new LOAD(new ImmediateInteger(0), Register.R0));
            compiler.addInstruction(new SUB(Register.getR(currentRegister), Register.R0));
            compiler.addInstruction(new LOAD(Register.R0, Register.getR(currentRegister)));
        } else {
            compiler.addInstruction(new LOAD(new ImmediateFloat(0), Register.R0));
            compiler.addInstruction(new SUB(Register.getR(currentRegister), Register.R0));
            compiler.addInstruction(new LOAD(Register.R0, Register.getR(currentRegister)));
        }
    }
}
