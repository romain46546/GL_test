package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl24
 * @date 01/01/2026
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type leftType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        String op = getOperatorName();
        boolean numericLeft = leftType.isInt() || leftType.isFloat();
        boolean numericRight = rightType.isInt() || rightType.isFloat();
        if ("==".equals(op) || "!=".equals(op)) {
            if (leftType.isBoolean() && rightType.isBoolean()) {
                setType(compiler.environmentType.BOOLEAN);
                return compiler.environmentType.BOOLEAN;
            }
            if (numericLeft && numericRight) {
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
                }
                setType(compiler.environmentType.BOOLEAN);
                return compiler.environmentType.BOOLEAN;
            }
            throw new ContextualError("types incompatible pour la comparison", getLocation());
        }
        if (!numericLeft || !numericRight) {
            throw new ContextualError("il faut ordonner", getLocation());
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
        }
        setType(compiler.environmentType.BOOLEAN);
        return compiler.environmentType.BOOLEAN;
    }


}
