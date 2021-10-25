var asmapi = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var attackEntityFrom = asmapi.mapMethod('func_70097_a');
var canBlockDamageSource = asmapi.mapMethod('func_184583_d');
var blockUsingShield = asmapi.mapMethod('func_190629_c');

function initializeCoreMod() {
	return {
		'damage': {
			'target': {
				'type': 'CLASS',
				'name': 'net.minecraft.entity.LivingEntity'
			},
			'transformer': function(classNode) {
				return patchShieldLogic(classNode);
			}
		}
	}
}

function patchShieldLogic(classNode) {
	var Opcodes = Java.type('org.objectweb.asm.Opcodes');
	var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
	var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
	var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
	var JumpInsnNode = Java.type('org.objectweb.asm.tree.JumpInsnNode');
	var owner = "com/tome/bettershields/Hooks";
	var methods = classNode.methods;
	var method = null;
	for (var i in methods) {
		if (methods[i].name == attackEntityFrom) {
			method = methods[i];
			break;
		}
	}

	var target = findFirstMethodReference(method, Opcodes.INVOKEVIRTUAL, canBlockDamageSource);

	for (var i = 0; i < 6; i++) {
		target = target.getPrevious();
	}

	// add f1 = Hooks.getDamageReduction(this, source, amount)
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.ALOAD, 0));
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.ALOAD, 1));
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FLOAD, 2));
	method.instructions.insertBefore(target, new MethodInsnNode(Opcodes.INVOKESTATIC, owner, 'getDamageReduction', '(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/DamageSource;F)F', false));
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FSTORE, 5));

	// add amount -= f1
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FLOAD, 2));
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FLOAD, 5));
	method.instructions.insertBefore(target, new InsnNode(Opcodes.FSUB));
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FSTORE, 2));

	// add flag = f1 > 0
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FLOAD, 5));
	method.instructions.insertBefore(target, new InsnNode(Opcodes.FCONST_0));
	method.instructions.insertBefore(target, new InsnNode(Opcodes.FCMPL));
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.ISTORE, 4));

	// add amount == 0 to the if
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FLOAD, 2));
	method.instructions.insertBefore(target, new InsnNode(Opcodes.FCONST_0));
	method.instructions.insertBefore(target, new InsnNode(Opcodes.FCMPL));
	var label = target.getNext().getNext().getNext().label;
	method.instructions.insertBefore(target, new JumpInsnNode(Opcodes.IFNE, label));

	// add amount = f1
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FLOAD, 5));
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.FSTORE, 2));

	// add flag = false
	method.instructions.insertBefore(target, new InsnNode(Opcodes.ICONST_0));
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.ISTORE, 4));

	// return whatever Hooks.damageEntityFromReturn returns
	target = findLastInstruction(method, Opcodes.IRETURN).getPrevious();
	method.instructions.insertBefore(target, new VarInsnNode(Opcodes.ALOAD, 1));
	method.instructions.insert(target, new MethodInsnNode(Opcodes.INVOKESTATIC, owner, 'damageEntityFromReturn', '(Lnet/minecraft/util/DamageSource;Z)Z', false));
	return classNode;
}

function findFirstMethodReference(method, opcode, name) {
	var instructions = method.instructions;
	for (var i = 0; i < instructions.size(); i++) {
		var instruction = instructions.get(i);
		if (instruction.getOpcode() == opcode && instruction.name == name) {
			return instruction;
		}
	}
}

function findLastInstruction(method, opcode) {
	var instructions = method.instructions;
	var lastInstruction = null;
	for (var i = 0; i < instructions.size(); i++) {
		var instruction = instructions.get(i);
		if (instruction.getOpcode() == opcode) {
			lastInstruction = instruction;
		}
	}
	return lastInstruction;
}
