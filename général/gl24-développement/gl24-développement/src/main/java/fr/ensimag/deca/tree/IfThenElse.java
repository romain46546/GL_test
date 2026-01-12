package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import org.apache.commons.lang.Validate;

/**
 * Full if/else if/else statement.
 *
 * @author gl24
 * @date 01/01/2026
 */
public class IfThenElse extends AbstractInst {
    
    private final AbstractExpr condition; 
    private final ListInst thenBranch;
    private ListInst elseBranch;

    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        condition.verifyCondition(compiler, localEnv, currentClass);
        thenBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
        elseBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
    }


    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        // Registre temporaire pour la condition
        int reg = 2;

        int id = compiler.nextLabelId();
        Label labelElse = new Label("else_" + id);
        Label labelEnd = new Label("endif_" + id);

        // Évaluer la condition
        condition.codeGenExp(compiler, reg);

        // if condition == 0 → else
        compiler.addInstruction(new CMP(
                new ImmediateInteger(0),
                Register.getR(reg)
        ));
        compiler.addInstruction(new BEQ(labelElse));

        // THEN
        thenBranch.codeGenListInst(compiler);
        compiler.addInstruction(new BRA(labelEnd));

        // ELSE
        compiler.addLabel(labelElse);
        if (elseBranch != null) {
            elseBranch.codeGenListInst(compiler);
        }

        // END IF
        compiler.addLabel(labelEnd);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("if(");
        this.condition.decompile(s);
        s.print("){");
        this.thenBranch.decompile(s);
        s.print("} else {");
        this.elseBranch.decompile(s);
        s.print("}");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
}
