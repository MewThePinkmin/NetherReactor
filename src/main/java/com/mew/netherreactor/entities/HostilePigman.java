package com.mew.netherreactor.entities;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class HostilePigman extends ZombieEntity {
	private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID
			.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(
			ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05D, AttributeModifier.Operation.ADDITION))
					.setSaved(false);
	private int angerLevel;
	private int randomSoundDelay;
	private UUID angerTargetUUID;

	public HostilePigman(EntityType<? extends HostilePigman> entityType, World world) {
		super(entityType, world);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
	}
	
	public void setRevengeTarget(@Nullable LivingEntity livingBase) {
		super.setRevengeTarget(livingBase);
		if (livingBase != null) {
			this.angerTargetUUID = livingBase.getUniqueID();
		}
	}

	protected void applyEntityAI() {
		this.goalSelector.addGoal(2, new ZombieAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.targetSelector.addGoal(1, new HostilePigman.HurtByAggressorGoal(this));
		this.targetSelector.addGoal(2, new HostilePigman.TargetAggressorGoal(this));
	}

	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.23F);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected void updateAITasks() {
		IAttributeInstance iattributeinstance = this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		LivingEntity livingentity = this.getRevengeTarget();
		if (this.isAngry()) {
			if (!this.isChild() && !iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
				iattributeinstance.applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
			}

			--this.angerLevel;
			LivingEntity livingentity1 = livingentity != null ? livingentity : this.getAttackTarget();
			if (!this.isAngry() && livingentity1 != null) {
				if (!this.canEntityBeSeen(livingentity1)) {
					this.setRevengeTarget((LivingEntity) null);
					this.setAttackTarget((LivingEntity) null);
				} else {
					this.angerLevel = this.func_400_rand();
				}
			}
		} else if (iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
			iattributeinstance.removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
		}

		if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
			this.playSound(SoundEvents.ENTITY_ZOMBIE_PIGMAN_ANGRY, this.getSoundVolume() * 2.0F,
					((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
		}

		if (this.isAngry() && this.angerTargetUUID != null && livingentity == null) {
			PlayerEntity playerentity = this.world.getPlayerByUuid(this.angerTargetUUID);
			this.setRevengeTarget(playerentity);
			this.attackingPlayer = playerentity;
			this.recentlyHit = this.getRevengeTimer();
		}

		super.updateAITasks();
	}

	public static boolean func_223337_b(EntityType<HostilePigman> entityType, IWorld iworld, SpawnReason reason,
			BlockPos blockPos, Random rand) {
		return iworld.getDifficulty() != Difficulty.PEACEFUL;
	}

	public boolean isNotColliding(IWorldReader worldIn) {
		return worldIn.func_226668_i_(this) && !worldIn.containsAnyLiquid(this.getBoundingBox());
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putShort("Anger", (short) this.angerLevel);
		if (this.angerTargetUUID != null) {
			compound.putString("HurtBy", this.angerTargetUUID.toString());
		} else {
			compound.putString("HurtBy", "");
		}

	}
	
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.angerLevel = compound.getShort("Anger");
		String s = compound.getString("HurtBy");
		if (!s.isEmpty()) {
			this.angerTargetUUID = UUID.fromString(s);
			PlayerEntity playerentity = this.world.getPlayerByUuid(this.angerTargetUUID);
			this.setRevengeTarget(playerentity);
			if (playerentity != null) {
				this.attackingPlayer = playerentity;
				this.recentlyHit = this.getRevengeTimer();
			}
		}

	}
	
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) {
			return false;
		} else {
			Entity entity = source.getTrueSource();
			if (entity instanceof PlayerEntity && !((PlayerEntity) entity).isCreative()
					&& this.canEntityBeSeen(entity)) {
				this.anger_sound_revenge((LivingEntity) entity);
			}
			return super.attackEntityFrom(source, amount);
		}
	}

	private boolean anger_sound_revenge(LivingEntity livingEntity) {
		this.angerLevel = this.func_400_rand();
		this.randomSoundDelay = this.rand.nextInt(40);
		this.setRevengeTarget(livingEntity);
		return true;
	}

	private int func_400_rand() {
		return 400 + this.rand.nextInt(400);
	}

	private boolean isAngry() {
		return true;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ZOMBIE_PIGMAN_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_ZOMBIE_PIGMAN_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ZOMBIE_PIGMAN_DEATH;
	}
	
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
	}

	protected ItemStack getSkullDrop() {
		return null;
	}

	public boolean isPreventingPlayerRest(PlayerEntity playerIn) {
		return this.isAngry();
	}

	static class HurtByAggressorGoal extends HurtByTargetGoal {
		public HurtByAggressorGoal(HostilePigman hPig) {
			super(hPig);
			this.setCallsForHelp(new Class[] { ZombieEntity.class });
		}

		protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn) {
			if (mobIn instanceof HostilePigman && this.goalOwner.canEntityBeSeen(targetIn)
					&& ((HostilePigman) mobIn).anger_sound_revenge(targetIn)) {
				mobIn.setAttackTarget(targetIn);
			}

		}
	}

	static class TargetAggressorGoal extends NearestAttackableTargetGoal<PlayerEntity> {
		public TargetAggressorGoal(HostilePigman hPig) {
			super(hPig, PlayerEntity.class, true);
		}
		
		public boolean shouldExecute() {
			return ((HostilePigman) this.goalOwner).isAngry() && super.shouldExecute();
		}
	}
}
