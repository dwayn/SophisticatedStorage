package net.p3pp3rf1y.sophisticatedstorage.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedstorage.block.BarrelBlock;
import net.p3pp3rf1y.sophisticatedstorage.block.StorageBlockEntity;

public class BarrelBlockEntityRenderer implements BlockEntityRenderer<StorageBlockEntity> {
	public BarrelBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

	}

	@Override
	public void render(StorageBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		if (!blockEntity.hasDynamicRenderer()) {
			return;
		}

		Minecraft minecraft = Minecraft.getInstance();
		ItemStack item = blockEntity.getRenderInfo().getItemDisplayRenderInfo().getItem();

		if (item.isEmpty()) {
			return;
		}

		Direction facing = blockEntity.getBlockState().getValue(BarrelBlock.FACING);
		BakedModel itemModel = minecraft.getItemRenderer().getModel(item, null, minecraft.player, 0);

		poseStack.pushPose();
		poseStack.translate(0.5, 0.5, 0.5);
		Vec3i normal = facing.getNormal();
		poseStack.translate(normal.getX() * 0.55, normal.getY() * 0.55, normal.getZ() * 0.55);
		poseStack.mulPose(facing.getRotation());
		if (facing.getAxis().isHorizontal()) {
			poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
		}
		poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
		poseStack.scale(0.75f, 0.75f, 0.75f);
		minecraft.getItemRenderer().render(item, ItemTransforms.TransformType.FIXED, false, poseStack, bufferSource, packedLight, packedOverlay, itemModel);
		poseStack.popPose();
	}
}