// package net.adventurez.entity.model;

// import software.bernie.geckolib.forgetofabric.ResourceLocation;
// import net.adventurez.entity.GryphonEntity;
// import software.bernie.geckolib.animation.model.AnimatedEntityModel;
// import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

// public class GryphonModel extends AnimatedEntityModel<GryphonEntity> {

//     private final AnimatedModelRenderer Body;
// 	private final AnimatedModelRenderer BodyFront;
// 	private final AnimatedModelRenderer Head;
// 	private final AnimatedModelRenderer Ear;
// 	private final AnimatedModelRenderer Tail;
// 	private final AnimatedModelRenderer LeftLegFront;
// 	private final AnimatedModelRenderer LeftFootFront;
// 	private final AnimatedModelRenderer lfooter1;
// 	private final AnimatedModelRenderer lfooter2;
// 	private final AnimatedModelRenderer RightLegFront;
// 	private final AnimatedModelRenderer RightFootFront;
// 	private final AnimatedModelRenderer rfooter3;
// 	private final AnimatedModelRenderer rfooter4;
// 	private final AnimatedModelRenderer Neck;
// 	private final AnimatedModelRenderer Neck2;
// 	private final AnimatedModelRenderer Saddle;
// 	private final AnimatedModelRenderer RightLeg;
// 	private final AnimatedModelRenderer rightfoot;
// 	private final AnimatedModelRenderer LeftLeg;
// 	private final AnimatedModelRenderer leftfoot;
// 	private final AnimatedModelRenderer RightWing;
// 	private final AnimatedModelRenderer RightWing2;
// 	private final AnimatedModelRenderer RightWing3;
// 	private final AnimatedModelRenderer LeftWing;
// 	private final AnimatedModelRenderer LeftWing2;
// 	private final AnimatedModelRenderer LeftWing3;

//     public GryphonModel()
//     {
//         textureWidth = 128;
// 		textureHeight = 128;
// 		Body = new AnimatedModelRenderer(this);
// 		Body.setRotationPoint(0.0F, 10.0F, 0.0F);
// 		Body.setTextureOffset(0, 36).addBox(-4.0F, -5.0F, -5.0F, 8.0F, 9.0F, 16.0F, 0.0F, false);
// 		Body.setModelRendererName("Body");
// 		this.registerModelRenderer(Body);

// 		BodyFront = new AnimatedModelRenderer(this);
// 		BodyFront.setRotationPoint(0.0F, 0.0F, -5.0F);
// 		Body.addChild(BodyFront);
// 		setRotationAngle(BodyFront, -0.3491F, 0.0F, 0.0F);
// 		BodyFront.setTextureOffset(0, 61).addBox(-5.0F, -7.0F, -7.0F, 10.0F, 11.0F, 9.0F, 0.0F, false);
// 		BodyFront.setModelRendererName("BodyFront");
// 		this.registerModelRenderer(BodyFront);

// 		Head = new AnimatedModelRenderer(this);
// 		Head.setRotationPoint(0.0F, -4.0F, -11.0F);
// 		Head.setTextureOffset(40, 60).addBox(-3.0F, -5.0F, -2.0F, 6.0F, 7.0F, 8.0F, 0.0F, false);
// 		Head.setTextureOffset(66, 20).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
// 		Head.setModelRendererName("Head");
// 		this.registerModelRenderer(Head);

// 		Ear = new AnimatedModelRenderer(this);
// 		Ear.setRotationPoint(0.0F, -3.0F, 3.0F);
// 		Head.addChild(Ear);
// 		setRotationAngle(Ear, -1.0908F, 0.0F, 0.0F);
// 		Ear.setTextureOffset(50, 75).addBox(3.0F, -5.0F, -1.0F, 1.0F, 5.0F, 3.0F, 0.0F, false);
// 		Ear.setTextureOffset(0, 61).addBox(-4.0F, -5.0F, -1.0F, 1.0F, 5.0F, 3.0F, 0.0F, false);
// 		Ear.setModelRendererName("Ear");
// 		this.registerModelRenderer(Ear);

// 		Tail = new AnimatedModelRenderer(this);
// 		Tail.setRotationPoint(0.0F, 7.0F, 10.0F);
// 		setRotationAngle(Tail, 0.6545F, 0.0F, 0.0F);
// 		Tail.setTextureOffset(67, 13).addBox(-1.0F, 8.5F, -0.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
// 		Tail.setTextureOffset(14, 14).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 9.0F, 1.0F, 0.0F, false);
// 		Tail.setModelRendererName("Tail");
// 		this.registerModelRenderer(Tail);

// 		LeftLegFront = new AnimatedModelRenderer(this);
// 		LeftLegFront.setRotationPoint(4.5F, 7.0F, -6.5F);
// 		setRotationAngle(LeftLegFront, -0.3491F, 0.0F, 0.0F);
// 		LeftLegFront.setTextureOffset(44, 75).addBox(-0.5F, 8.9396F, -1.158F, 1.0F, 9.0F, 2.0F, 0.0F, false);
// 		LeftLegFront.setTextureOffset(0, 14).addBox(-1.5F, -1.0604F, -2.158F, 3.0F, 10.0F, 4.0F, 0.0F, false);
// 		LeftLegFront.setModelRendererName("LeftLegFront");
// 		this.registerModelRenderer(LeftLegFront);

// 		LeftFootFront = new AnimatedModelRenderer(this);
// 		LeftFootFront.setRotationPoint(0.0F, 16.9396F, -0.158F);
// 		LeftLegFront.addChild(LeftFootFront);
// 		setRotationAngle(LeftFootFront, 0.3491F, 0.0F, 0.0F);
// 		LeftFootFront.setTextureOffset(73, 17).addBox(-1.5F, 0.4627F, -1.3762F, 3.0F, 1.0F, 2.0F, 0.0F, false);
// 		LeftFootFront.setTextureOffset(63, 76).addBox(-0.5F, 0.4627F, -4.3762F, 1.0F, 1.0F, 3.0F, 0.0F, false);
// 		LeftFootFront.setModelRendererName("LeftFootFront");
// 		this.registerModelRenderer(LeftFootFront);

// 		lfooter1 = new AnimatedModelRenderer(this);
// 		lfooter1.setRotationPoint(0.0F, 0.4627F, -1.3762F);
// 		LeftFootFront.addChild(lfooter1);
// 		setRotationAngle(lfooter1, 0.0F, -0.3491F, 0.0F);
// 		lfooter1.setTextureOffset(75, 13).addBox(0.5F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
// 		lfooter1.setModelRendererName("lfooter1");
// 		this.registerModelRenderer(lfooter1);

// 		lfooter2 = new AnimatedModelRenderer(this);
// 		lfooter2.setRotationPoint(0.0F, 0.4627F, -1.3762F);
// 		LeftFootFront.addChild(lfooter2);
// 		setRotationAngle(lfooter2, 0.0F, 0.3491F, 0.0F);
// 		lfooter2.setTextureOffset(58, 75).addBox(-1.5F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
// 		lfooter2.setModelRendererName("lfooter2");
// 		this.registerModelRenderer(lfooter2);

// 		RightLegFront = new AnimatedModelRenderer(this);
// 		RightLegFront.setRotationPoint(-4.0F, 7.0F, -6.5F);
// 		setRotationAngle(RightLegFront, -0.3491F, 0.0F, 0.0F);
// 		RightLegFront.setTextureOffset(38, 75).addBox(-1.0F, 8.5976F, -1.2183F, 1.0F, 9.0F, 2.0F, 0.0F, false);
// 		RightLegFront.setTextureOffset(0, 0).addBox(-2.0F, -1.4024F, -2.2183F, 3.0F, 10.0F, 4.0F, 0.0F, false);
// 		RightLegFront.setModelRendererName("RightLegFront");
// 		this.registerModelRenderer(RightLegFront);

// 		RightFootFront = new AnimatedModelRenderer(this);
// 		RightFootFront.setRotationPoint(-0.5F, 16.5976F, -0.2183F);
// 		RightLegFront.addChild(RightFootFront);
// 		setRotationAngle(RightFootFront, 0.3491F, 0.0F, 0.0F);
// 		RightFootFront.setTextureOffset(29, 67).addBox(-1.5F, 0.4627F, -1.3762F, 3.0F, 1.0F, 2.0F, 0.0F, false);
// 		RightFootFront.setTextureOffset(74, 64).addBox(-0.5F, 0.4627F, -4.3762F, 1.0F, 1.0F, 3.0F, 0.0F, false);
// 		RightFootFront.setModelRendererName("RightFootFront");
// 		this.registerModelRenderer(RightFootFront);

// 		rfooter3 = new AnimatedModelRenderer(this);
// 		rfooter3.setRotationPoint(0.0F, 0.4627F, -1.3762F);
// 		RightFootFront.addChild(rfooter3);
// 		setRotationAngle(rfooter3, 0.0F, -0.3491F, 0.0F);
// 		rfooter3.setTextureOffset(10, 28).addBox(0.5F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
// 		rfooter3.setModelRendererName("rfooter3");
// 		this.registerModelRenderer(rfooter3);

// 		rfooter4 = new AnimatedModelRenderer(this);
// 		rfooter4.setRotationPoint(0.0F, 0.4627F, -1.3762F);
// 		RightFootFront.addChild(rfooter4);
// 		setRotationAngle(rfooter4, 0.0F, 0.3491F, 0.0F);
// 		rfooter4.setTextureOffset(10, 0).addBox(-1.5F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
// 		rfooter4.setModelRendererName("rfooter4");
// 		this.registerModelRenderer(rfooter4);

// 		Neck = new AnimatedModelRenderer(this);
// 		Neck.setRotationPoint(0.0F, 6.0F, -9.0F);
// 		Neck.setTextureOffset(66, 0).addBox(-3.0F, -3.7679F, -3.134F, 6.0F, 7.0F, 6.0F, 0.0F, false);
// 		Neck.setModelRendererName("Neck");
// 		this.registerModelRenderer(Neck);

// 		Neck2 = new AnimatedModelRenderer(this);
// 		Neck2.setRotationPoint(0.0F, -2.0F, 0.0F);
// 		Neck.addChild(Neck2);
// 		setRotationAngle(Neck2, -0.3491F, 0.0F, 0.0F);
// 		Neck2.setTextureOffset(0, 41).addBox(-2.0F, -6.7679F, -3.134F, 4.0F, 6.0F, 4.0F, 0.0F, false);
// 		Neck2.setModelRendererName("Neck2");
// 		this.registerModelRenderer(Neck2);

// 		Saddle = new AnimatedModelRenderer(this);
// 		Saddle.setRotationPoint(0.0F, 2.0F, 2.0F);
// 		Saddle.setTextureOffset(0, 61).addBox(-4.0F, 3.0F, -5.5F, 8.0F, 9.0F, 10.0F, 0.5F, false);
// 		Saddle.setModelRendererName("Saddle");
// 		this.registerModelRenderer(Saddle);

// 		RightLeg = new AnimatedModelRenderer(this);
// 		RightLeg.setRotationPoint(-4.0F, 11.0F, 9.0F);
// 		setRotationAngle(RightLeg, -0.3491F, 0.0F, 0.0F);
// 		RightLeg.setTextureOffset(68, 68).addBox(-1.0F, 4.0F, 3.0F, 3.0F, 7.0F, 3.0F, 0.0F, false);
// 		RightLeg.setTextureOffset(32, 38).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 9.0F, 4.0F, 0.0F, false);
// 		RightLeg.setModelRendererName("RightLeg");
// 		this.registerModelRenderer(RightLeg);

// 		rightfoot = new AnimatedModelRenderer(this);
// 		rightfoot.setRotationPoint(0.5F, 9.8533F, 4.5032F);
// 		RightLeg.addChild(rightfoot);
// 		setRotationAngle(rightfoot, 0.3927F, 0.0F, 0.0F);
// 		rightfoot.setTextureOffset(62, 38).addBox(-2.5F, 0.1467F, -3.0032F, 5.0F, 2.0F, 4.0F, 0.0F, false);
// 		rightfoot.setModelRendererName("rightfoot");
// 		this.registerModelRenderer(rightfoot);

// 		LeftLeg = new AnimatedModelRenderer(this);
// 		LeftLeg.setRotationPoint(3.0F, 9.0F, 10.0F);
// 		setRotationAngle(LeftLeg, -0.3491F, 0.0F, 0.0F);
// 		LeftLeg.setTextureOffset(66, 28).addBox(-1.0F, 5.6579F, 2.9397F, 3.0F, 7.0F, 3.0F, 0.0F, false);
// 		LeftLeg.setTextureOffset(0, 28).addBox(-1.0F, 0.0F, -1.0F, 3.0F, 9.0F, 4.0F, 0.0F, false);
// 		LeftLeg.setModelRendererName("LeftLeg");
// 		this.registerModelRenderer(LeftLeg);

// 		leftfoot = new AnimatedModelRenderer(this);
// 		leftfoot.setRotationPoint(0.5F, 12.0747F, 4.2476F);
// 		LeftLeg.addChild(leftfoot);
// 		setRotationAngle(leftfoot, 0.3927F, 0.0F, 0.0F);
// 		leftfoot.setTextureOffset(29, 61).addBox(-2.5F, -0.0311F, -2.7486F, 5.0F, 2.0F, 4.0F, 0.0F, false);
// 		leftfoot.setModelRendererName("leftfoot");
// 		this.registerModelRenderer(leftfoot);

// 		RightWing = new AnimatedModelRenderer(this);
// 		RightWing.setRotationPoint(-4.0F, 5.0F, -5.5F);
// 		setRotationAngle(RightWing, 0.2094F, -0.5236F, 1.1345F);
// 		RightWing.setTextureOffset(44, 44).addBox(-7.0F, -0.5F, 0.5F, 7.0F, 0.0F, 18.0F, 0.0F, false);
// 		RightWing.setTextureOffset(60, 66).addBox(-7.0F, -1.0F, -0.5F, 6.0F, 1.0F, 1.0F, 0.0F, false);
// 		RightWing.setModelRendererName("RightWing");
// 		this.registerModelRenderer(RightWing);

// 		RightWing2 = new AnimatedModelRenderer(this);
// 		RightWing2.setRotationPoint(-7.0F, -0.5F, 0.0F);
// 		RightWing.addChild(RightWing2);
// 		setRotationAngle(RightWing2, 0.0F, 0.0F, -2.5744F);
// 		RightWing2.setTextureOffset(0, 18).addBox(-12.0F, 0.0F, 0.5F, 12.0F, 0.0F, 18.0F, 0.0F, false);
// 		RightWing2.setTextureOffset(42, 20).addBox(-12.0F, -0.5F, -0.5F, 12.0F, 1.0F, 1.0F, 0.0F, false);
// 		RightWing2.setModelRendererName("RightWing2");
// 		this.registerModelRenderer(RightWing2);

// 		RightWing3 = new AnimatedModelRenderer(this);
// 		RightWing3.setRotationPoint(-12.0F, 0.0F, 0.0F);
// 		RightWing2.addChild(RightWing3);
// 		RightWing3.setTextureOffset(0, 0).addBox(-12.0F, 0.0F, 0.5F, 12.0F, 0.0F, 18.0F, 0.0F, false);
// 		RightWing3.setTextureOffset(42, 18).addBox(-12.0F, -0.5F, -0.5F, 12.0F, 1.0F, 1.0F, 0.0F, false);
// 		RightWing3.setModelRendererName("RightWing3");
// 		this.registerModelRenderer(RightWing3);

// 		LeftWing = new AnimatedModelRenderer(this);
// 		LeftWing.setRotationPoint(4.0F, 5.0F, -5.5F);
// 		setRotationAngle(LeftWing, 0.2094F, 0.5236F, -1.1345F);
// 		LeftWing.setTextureOffset(30, 42).addBox(0.0F, -0.5F, 0.5F, 7.0F, 0.0F, 18.0F, 0.0F, false);
// 		LeftWing.setTextureOffset(60, 64).addBox(0.0F, -1.0F, -0.5F, 7.0F, 1.0F, 1.0F, 0.0F, false);
// 		LeftWing.setModelRendererName("LeftWing");
// 		this.registerModelRenderer(LeftWing);

// 		LeftWing2 = new AnimatedModelRenderer(this);
// 		LeftWing2.setRotationPoint(7.0F, -0.5F, 0.0F);
// 		LeftWing.addChild(LeftWing2);
// 		setRotationAngle(LeftWing2, 0.0F, 0.0F, 2.5744F);
// 		LeftWing2.setTextureOffset(60, 62).addBox(0.0F, -0.5F, -0.5F, 12.0F, 1.0F, 1.0F, 0.0F, false);
// 		LeftWing2.setTextureOffset(24, 0).addBox(0.0F, 0.0F, 0.5F, 12.0F, 0.0F, 18.0F, 0.0F, false);
// 		LeftWing2.setModelRendererName("LeftWing2");
// 		this.registerModelRenderer(LeftWing2);

// 		LeftWing3 = new AnimatedModelRenderer(this);
// 		LeftWing3.setRotationPoint(12.0F, 0.0F, 0.0F);
// 		LeftWing2.addChild(LeftWing3);
// 		LeftWing3.setTextureOffset(42, 22).addBox(0.0F, -0.5F, -0.5F, 12.0F, 1.0F, 1.0F, 0.0F, false);
// 		LeftWing3.setTextureOffset(24, 24).addBox(0.0F, 0.0F, 0.5F, 12.0F, 0.0F, 18.0F, 0.0F, false);
// 		LeftWing3.setModelRendererName("LeftWing3");
// 		this.registerModelRenderer(LeftWing3);

// 		this.rootBones.add(Body);
// 		this.rootBones.add(Head);
// 		this.rootBones.add(Tail);
// 		this.rootBones.add(LeftLegFront);
// 		this.rootBones.add(RightLegFront);
// 		this.rootBones.add(Neck);
// 		this.rootBones.add(Saddle);
// 		this.rootBones.add(RightLeg);
// 		this.rootBones.add(LeftLeg);
// 		this.rootBones.add(RightWing);
// 		this.rootBones.add(LeftWing);
// 	}


//     @Override
//     public ResourceLocation getAnimationFileLocation()
//     {
//         return new ResourceLocation("adventurez", "animations/gryphon_animation.json");
//     }
// }