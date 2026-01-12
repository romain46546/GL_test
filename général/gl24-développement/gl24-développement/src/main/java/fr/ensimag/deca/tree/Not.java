package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;

/**
 *
 * @author gl24
 * @date 01/01/2026
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = getOperand().verifyExpr(compiler, localEnv, currentClass);
        if (!type.isBoolean()) {
            throw new ContextualError("Logical not on non-boolean expression", getLocation());
        }
        setType(compiler.environmentType.BOOLEAN);
        return compiler.environmentType.BOOLEAN;
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }

    @Override
    protected void codeGenExp(DecacCompiler compiler, int currentRegister) {
        // 1. Évaluer l'opérande et mettre son résultat dans Rn
        getOperand().codeGenExp(compiler, currentRegister);

        // 2. Appliquer le NOT logique
        // CMP #0, Rn
        compiler.addInstruction(new CMP(0, fr.ensimag.ima.pseudocode.Register.getR(currentRegister)));
        // SEQ Rn
        compiler.addInstruction(new SEQ(fr.ensimag.ima.pseudocode.Register.getR(currentRegister)));
    }

}
