var asmapi = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var attackEntityFrom = asmapi.mapMethod('func_70097_a');
var canBlockDamageSource = asmapi.mapMethod('func_184583_d');

function initializeCoreMod() {
    return {
        'damage': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.entity.LivingEntity'
            },
            'transformer': function(classNode) {
                return patchShieldLogic(classNode, 'attackEntityFrom');
            }
        }
    }
}

function patchShieldLogic(classNode, methodName) {
    var Opcodes = Java.type('org.objectweb.asm.Opcodes');
    var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
    var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
    var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
    var owner = "com/tome/bettershields/Hooks";
    var methods = classNode.methods;
    var method = null;
    for(var i in methods) {
        if(methods[i].name == attackEntityFrom) {
            method = methods[i];
            break;
        }
    }
    target = findFirstReference(method, Opcodes.INVOKEVIRTUAL, canBlockDamageSource);
    method.instructions.remove(target.getPrevious().getPrevious());
    method.instructions.remove(target.getPrevious());
    method.instructions.insertBefore(target, new VarInsnNode(Opcodes.ALOAD, 0));
    method.instructions.insertBefore(target, new VarInsnNode(Opcodes.ALOAD, 1));
    method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FLOAD, 2));
    method.instructions.insertBefore(target, new MethodInsnNode(Opcodes.INVOKESTATIC, owner, 'getDamageReduction', '(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/DamageSource;F)F', false));
    method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FSTORE, 5));
    method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FLOAD, 2));
    method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FLOAD, 5));
    method.instructions.insertBefore(target, new InsnNode(Opcodes.FSUB));
    method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FSTORE, 2));
    method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FLOAD, 5));
    method.instructions.insertBefore(target, new InsnNode(Opcodes.FCONST_1));
    method.instructions.insertBefore(target, new InsnNode(Opcodes.ICONST_2));
    method.instructions.insertBefore(target, new InsnNode(Opcodes.I2F));
    method.instructions.insertBefore(target, new InsnNode(Opcodes.FDIV));
    method.instructions.insertBefore(target, new InsnNode(Opcodes.FSUB));
    method.instructions.insertBefore(target, new InsnNode(Opcodes.F2I));
    method.instructions.insertBefore(target, new VarInsnNode(Opcodes.ISTORE, 4));
    method.instructions.insert(target, new InsnNode(Opcodes.ICONST_0));
    method.instructions.remove(target);
    return classNode;
}

function findFirstReference(method, opcode, name) {
    var instructions = method.instructions;
    for(var i = 0; i < instructions.size(); i++) {
        var instruction = instructions.get(i);
        if(instruction.getOpcode() == opcode && instruction.name == name) {
            return instruction;
        }
    }
}
