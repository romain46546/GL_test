package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl24
 * @date 01/01/2026
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type leftType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        boolean leftNumeric = leftType.isInt() || leftType.isFloat();
        boolean rightNumeric = rightType.isInt() || rightType.isFloat();
        if (!leftNumeric || !rightNumeric) {
            throw new ContextualError("types incompatible pour les operations arithmetiques ", getLocation());
        }
        if (leftType.isFloat() || rightType.isFloat()) {
            if (leftType.isInt()) {
                ConvFloat conv = new ConvFloat(getLeftOperand());
                conv.setLocation(getLeftOperand().getLocation());
                conv.setType(compiler.environmentType.FLOAT);
                setLeftOperand(conv);
            }
            if (rightType.isInt()) {
                ConvFloat conv = new ConvFloat(getRightOperand());
                conv.setLocation(getRightOperand().getLocation());
                conv.setType(compiler.environmentType.FLOAT);
                setRightOperand(conv);
            }
            setType(compiler.environmentType.FLOAT);
            return compiler.environmentType.FLOAT;
        }
        setType(compiler.environmentType.INT);
        return compiler.environmentType.INT;
    }

    @Override
    protected void codeGenExp(DecacCompiler compiler, int currentRegister) {
        int max = compiler.getCompilerOptions().getRegisters() - 1;
        if (max == currentRegister){
            getLeftOperand().codeGenExp(compiler, currentRegister);
            compiler.addInstruction(new PUSH(Register.getR(currentRegister)));
            getRightOperand().codeGenExp(compiler, currentRegister);
            compiler.addInstruction(new LOAD(Register.getR(currentRegister), Register.R0));
            compiler.addInstruction(new POP(Register.getR(currentRegister)));
            this.codeGenOpArith(compiler, currentRegister);
        } else {
            getLeftOperand().codeGenExp(compiler, currentRegister);
            getRightOperand().codeGenExp(compiler, currentRegister+1);
            this.codeGenOpArith(compiler, currentRegister);
        }
    }

    protected void codeGenOpArith(DecacCompiler compiler, int currentRegister){
        throw new UnsupportedOperationException("not yet implemented");
    }
}
