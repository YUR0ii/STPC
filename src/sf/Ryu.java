package sf;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;

import javax.imageio.ImageIO;

public class Ryu extends Character
{
	public Ryu()
	{
		super("Ryu", 4, 7, 5, 24);
	}

	@Override
	Animation Stand()
	{
		try{
			return new Animation(0, new int[] {0,10,20,30,40},
					new Body[]
							{
									new Body(new Hurtbox[]
											{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
											}),
									new Body(new Hurtbox[]
											{
													new Hurtbox(new Point(5,0), new Dimension(40,96)),
													new Hurtbox(new Point(24,94), new Dimension(24,20))
											}),
									new Body(new Hurtbox[]
											{
													new Hurtbox(new Point(5,0), new Dimension(40,96)),
													new Hurtbox(new Point(24,94), new Dimension(24,20))
											}),
									new Body(new Hurtbox[]
											{
													new Hurtbox(new Point(5,0), new Dimension(40,96)),
													new Hurtbox(new Point(24,94), new Dimension(24,20))
											})
							},
					new BufferedImage[]
							{
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/neutral/neutral (3).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/neutral/neutral (4).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/neutral/neutral (5).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/neutral/neutral (6).png"))
							});
		}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Animation Crouch()
	{
		try{
		return new Animation(1, new int[] {0},
				new Body[]
						{
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(8,0), new Dimension(45,59)),
											new Hurtbox(new Point(27,55), new Dimension(24,24))
										})
						},
						new BufferedImage[]
								{
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/crouch (2).png"))
								});
			}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Animation Block()
	{
		try{
		return new Animation(2, new int[] {0},
				new Body[] {
						new Body(new Hurtbox[]
								{
									new Hurtbox(new Point(11,0), new Dimension(40,96)),
									new Hurtbox(new Point(29,94), new Dimension(23,20))
								})
		},
				new BufferedImage[]
						{
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/highblock.png"))
						});
	}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Animation BlockC()
	{
		try {
		return new Animation(3, new int[] {0},
				new Body[]
						{
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(15,0), new Dimension(45,75)),
											new Hurtbox(new Point(34,55), new Dimension(24,24))
										})
						},
						new BufferedImage[]
								{
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/lowblock.png"))
								});
			}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Animation Damage()
	{
		try
		{
		return new Animation(4, new int[] {0},
				new Body[] {
						new Body(new Hurtbox[]
								{
									new Hurtbox(new Point(5,0), new Dimension(40,96)),
									new Hurtbox(new Point(24,94), new Dimension(24,20))
								})
		},
				new BufferedImage[]
						{
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/highdmg (1).png"))
						});
	}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Animation DamageC()
	{
		try
		{
		return new Animation(5, new int[] {0},
				new Body[] {
						new Body(new Hurtbox[]
								{
									new Hurtbox(new Point(8,0), new Dimension(45,59)),
									new Hurtbox(new Point(27,55), new Dimension(24,24))
								})
		},
				new BufferedImage[]
						{
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/l dam (2).png"))
						});
	}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Animation WalkF()
	{
		try {
		return new Animation(6, new int[] {0,5,10,15,20,25,30},
				new Body[]
						{
									new Body(new Hurtbox[]
											{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
											}),
									new Body(new Hurtbox[]
											{
													new Hurtbox(new Point(5,0), new Dimension(40,96)),
													new Hurtbox(new Point(24,94), new Dimension(24,20))
											}),
									new Body(new Hurtbox[]
											{
													new Hurtbox(new Point(5,0), new Dimension(40,96)),
													new Hurtbox(new Point(24,94), new Dimension(24,20))
											}),
									new Body(new Hurtbox[]
											{
													new Hurtbox(new Point(5,0), new Dimension(40,96)),
													new Hurtbox(new Point(24,94), new Dimension(24,20))
											}),
									new Body(new Hurtbox[]
											{
													new Hurtbox(new Point(5,0), new Dimension(40,96)),
													new Hurtbox(new Point(24,94), new Dimension(24,20))
											}),
									new Body(new Hurtbox[]
											{
													new Hurtbox(new Point(5,0), new Dimension(40,96)),
													new Hurtbox(new Point(24,94), new Dimension(24,20))
											})
						},
						new BufferedImage[]
								{
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/front/foward (1).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/front/foward (2).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/front/foward (3).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/front/foward (4).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/front/foward (5).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/front/foward (6).png")),
								});
			}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Animation WalkB()
	{
		try {
		return new Animation(7, new int[] {0,5,10,15,20,25,30},
				new Body[]
						{
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										})
						},
						new BufferedImage[]
								{
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/back/back (1).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/back/back (2).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/back/back (3).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/back/back (4).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/back/back (5).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/back/back (6).png")),
								});
			}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Animation Jump()
	{
		try {
		return new Animation(8, new int[] {0,6,12,18,24,30,36,42},
				new Body[] {
						new Body(new Hurtbox[]
								{
									new Hurtbox(new Point(1,40), new Dimension(40,55)),
									new Hurtbox(new Point(31,72), new Dimension(24,21))
								}),
						new Body(new Hurtbox[]
								{
										new Hurtbox(new Point(1,40), new Dimension(40,55)),
										new Hurtbox(new Point(31,72), new Dimension(24,21))
								}),
						new Body(new Hurtbox[]
								{
										new Hurtbox(new Point(1,40), new Dimension(40,55)),
										new Hurtbox(new Point(31,72), new Dimension(24,21))
								}),
						new Body(new Hurtbox[]
								{
										new Hurtbox(new Point(1,40), new Dimension(40,55)),
										new Hurtbox(new Point(31,72), new Dimension(24,21))
								}),
						new Body(new Hurtbox[]
								{
										new Hurtbox(new Point(1,40), new Dimension(40,55)),
										new Hurtbox(new Point(31,72), new Dimension(24,21))
								}),
						new Body(new Hurtbox[]
								{
										new Hurtbox(new Point(1,40), new Dimension(40,55)),
										new Hurtbox(new Point(31,72), new Dimension(24,21))
								}),
						new Body(new Hurtbox[]
								{
										new Hurtbox(new Point(1,40), new Dimension(40,55)),
										new Hurtbox(new Point(31,72), new Dimension(24,21))
								})		},
				new BufferedImage[]
						{
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/neutral (2).png")),
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/neutral (3).png")),
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/neutral (4).png")),
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/neutral (5).png")),
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/neutral (6).png")),
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/neutral (7).png")),
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/neutral (1).png"))
						});
	}catch(Exception e){System.out.println("oh no"); return null;}
	}

	@Override
	Animation Knockdown()
	{
		try{
		return new Animation(9, new int[] {0},
				new Body[]
						{
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(0,0), new Dimension(74,66), true),
										})
						},
						new BufferedImage[]
								{
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/airdmg.png"))
								});
			}catch(Exception e){System.out.println("oh no"); return null;}

	}



	@Override
	Animation Wakeup()
	{
		try{
		return new Animation(10, new int[] {0,15,30,40,50,60},
				new Body[]
						{
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96), true),
												new Hurtbox(new Point(24,94), new Dimension(24,20), true)
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96), true),
												new Hurtbox(new Point(24,94), new Dimension(24,20), true)
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96), true),
												new Hurtbox(new Point(24,94), new Dimension(24,20), true)
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96), true),
												new Hurtbox(new Point(24,94), new Dimension(24,20), true)
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96), true),
												new Hurtbox(new Point(24,94), new Dimension(24,20), true)
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96), true),
												new Hurtbox(new Point(24,94), new Dimension(24,20), true)
										})
						},
						new BufferedImage[]
								{
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/down (1).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/recovery (2).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/recovery (3).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/recovery (4).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/recovery (5).png"))
								});
			}catch(Exception e){System.out.println("oh no"); return null;}
	}

	@Override
	Animation GrabF()
	{
		try{
		return new Animation(11, new int[] {0,10,20,30,40,50},
				new Body[]
						{
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										})
						},
						new BufferedImage[]
								{
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/throws/throwp (1).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/throws/throwp (2).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/throws/throwp (3).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/throws/throwp (4).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/throws/throwp (5).png"))
								});
			}catch(Exception e){System.out.println("oh no"); return null;}
	}

	@Override
	Animation GrabB()
	{
		try{
		return new Animation(12, new int[] {0,10,20,30,40,50},
				new Body[]
						{
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										}),
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										})
						},
						new BufferedImage[]
								{
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/throws/throwk (1).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/throws/throwk (2).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/throws/throwk (3).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/throws/throwk (4).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/throws/throwk (5).png"))
								});
			}catch(Exception e){System.out.println("oh no"); return null;}
	}

	@Override
	Animation Grabbed()
	{
		try{
		return new Animation(13, new int[] {0},
				new Body[]
						{
								new Body(new Hurtbox[]
										{
												new Hurtbox(new Point(5,0), new Dimension(40,96)),
												new Hurtbox(new Point(24,94), new Dimension(24,20))
										})
						},
						new BufferedImage[]
								{
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/airdmg.png"))
								});
			}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Animation BlockDmg()
	{
		try
		{
		return new Animation(50, new int[] {0},
				new Body[] {
						new Body(new Hurtbox[]
								{
									new Hurtbox(new Point(11,0), new Dimension(40,96)),
									new Hurtbox(new Point(29,94), new Dimension(23,5))
								})
		},
				new BufferedImage[]
						{
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/highblock2.png"))
						});
	}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Animation BlockDmgC()
	{
		try
		{
		return new Animation(51, new int[] {0},
				new Body[] {
						new Body(new Hurtbox[]
								{
									new Hurtbox(new Point(15,0), new Dimension(45,75)),
									new Hurtbox(new Point(34,55), new Dimension(6,6))
								})
		},
				new BufferedImage[]
						{
								ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/damage/lowblock (2).png"))
						});
	}catch(Exception e){System.out.println("oh no"); return null;}
	}

	@Override
	Attack P7()
	{
		try{
		return new Attack("Stand LP Close",
				new Hitbox[]
				{
						new Hitbox(new Point(40,100), new Dimension(38,22), AttackType.L, 3, 4, 2300, false, false)
				},
				new Animation(17, new int[] {3,7,14},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(0,0), new Dimension(41,96)),
													new Hurtbox(new Point(18,93), new Dimension(24,22))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(0,0), new Dimension(41,96)),
													new Hurtbox(new Point(18,93), new Dimension(24,22))
												})
								},
								new BufferedImage[]
										{
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/closelp(1).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/closelp (2).png"))
										}),
				12);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack P8()
	{
		try{
		return new Attack("Stand MP Far",
				new Hitbox[]
				{
						new Hitbox(new Point(40,86), new Dimension(68,18), AttackType.M, 4, 4, 3300, false, false)
				},
				new Animation(18, new int[] {0,4,8,15},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(16,0), new Dimension(40,100)),
													new Hurtbox(new Point(34,98), new Dimension(24,24))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(21,0), new Dimension(40,100)),
													new Hurtbox(new Point(39,98), new Dimension(24,24))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(16,0), new Dimension(40,100)),
													new Hurtbox(new Point(34,98), new Dimension(24,24))
												})
								},
								new BufferedImage[]
										{
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/far/mphp (1).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/far/mphp (3).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/far/mphp (2).png")),
										}),
				14);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack P9()
	{
		try {
		return new Attack("Stand HP Close",
				new Hitbox[]
				{
						new Hitbox(new Point(36,76), new Dimension(62,33), AttackType.H, 4, 2, 2150, false, false),
						new Hitbox(new Point(30,88), new Dimension(72,53), AttackType.H, 6, 6, 2150, false, false)
				},
				new Animation(19, new int[] {0,4,6,36},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(21,0), new Dimension(40,100)),
													new Hurtbox(new Point(40,100), new Dimension(24,24))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(17,0), new Dimension(40,100)),
													new Hurtbox(new Point(34,100), new Dimension(24,24))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(14,0), new Dimension(40,100)),
													new Hurtbox(new Point(32,100), new Dimension(24,24))
												})
								},
								new BufferedImage[]
										{
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/clsehp.png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/clsehp (1).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/clsehp (2).png"))
										}),
				35);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack K4()
	{
		try {
		return new Attack("Stand LK Close",
				new Hitbox[]
				{
						new Hitbox(new Point(26,1), new Dimension(66,54), AttackType.L, 6, 6, 2600, false, false)
				},
				new Animation(14, new int[] {0,6,12,17},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(0,0), new Dimension(40,100)),
													new Hurtbox(new Point(17,100), new Dimension(24,24))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(-10,0), new Dimension(40,100)),
													new Hurtbox(new Point(17,100), new Dimension(24,24))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(0,0), new Dimension(40,100)),
													new Hurtbox(new Point(17,100), new Dimension(24,24))
												})
								},
								new BufferedImage[]
										{
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/closelk (1).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/closelk (2).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/closelk (1).png"))
										}),
				16);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack K5()
	{
		try {
		return new Attack("Stand MK Far",
				new Hitbox[]
				{
						new Hitbox(new Point(50,86), new Dimension(68,38), AttackType.M, 8, 8, 3300, false, false)
				},
				new Animation(15, new int[] {0,8,16,24},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(14,0), new Dimension(45,65)),
													new Hurtbox(new Point(5,50), new Dimension(42,50)),
													new Hurtbox(new Point(13,97), new Dimension(30,24))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(36,0), new Dimension(44,100)),
													new Hurtbox(new Point(11,50), new Dimension(42,50)),
													new Hurtbox(new Point(3,97), new Dimension(30,24))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(14,0), new Dimension(45,65)),
													new Hurtbox(new Point(5,50), new Dimension(42,50)),
													new Hurtbox(new Point(13,97), new Dimension(30,24))
												})
								},
								new BufferedImage[]
										{
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/far/lkmkfar (1).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/far/lkmkfar (2).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/far/lkmkfar (1).png"))
										}),
				23);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack K6()
	{
		try {
		return new Attack("Stand HK Close",
				new Hitbox[]
				{
						new Hitbox(new Point(18,70), new Dimension(45,86), AttackType.H, 8, 8, 2150, false, false),
						new Hitbox(new Point(25,62), new Dimension(68,49), AttackType.H, 16, 4, 2150, false, false)
				},
				new Animation(16, new int[] {0,8,16,20,32},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(0,0), new Dimension(40,100)),
													new Hurtbox(new Point(17,98), new Dimension(24,24))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(-10,0), new Dimension(40,100)),
													new Hurtbox(new Point(7,92), new Dimension(38,45))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(-10,0), new Dimension(40,100)),
													new Hurtbox(new Point(12,78), new Dimension(68,46))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(0,0), new Dimension(39,98)),
													new Hurtbox(new Point(17,98), new Dimension(24,24))
												})
								},
								new BufferedImage[]
										{
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/closehk (1).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/closehk (2).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/closehk (4).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/close/closehk (7).png"))
										}),
				31);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack G1()
	{
		try{
		return new Attack("LGrab",
				new Hitbox[]
				{
						new Hitbox(new Point(68,63), new Dimension(20,13), 5300, 2, 6, false)
				},
				new Animation(41, new int[] {6,8,32},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(5,0), new Dimension(40,96)),
													new Hurtbox(new Point(24,94), new Dimension(24,20))
												}),
										new Body(new Hurtbox[]
												{
														new Hurtbox(new Point(5,0), new Dimension(40,96)),
														new Hurtbox(new Point(24,94), new Dimension(24,20))
												})
								},
						new BufferedImage[]
								{
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/far/lp (1).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/far/lp (2).png"))
								}),
				30);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack G2()
	{
		try{
		return new Attack("RGrab",
				new Hitbox[]
				{
						new Hitbox(new Point(68,63), new Dimension(20,13), 5300, 2, 6, true)
				},
				new Animation(42, new int[] {6,8,32},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(5,0), new Dimension(40,96)),
													new Hurtbox(new Point(24,94), new Dimension(24,20))
												}),
										new Body(new Hurtbox[]
												{
														new Hurtbox(new Point(5,0), new Dimension(40,96)),
														new Hurtbox(new Point(24,94), new Dimension(24,20))
												})
								},
						new BufferedImage[]
								{
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/far/lp (1).png")),
										ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/standing/far/lp (2).png"))
								}),
				30);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack S3()
	{
		try {
		return new Attack("Hadouken",
				new Hitbox[]
				{
						new Hitbox(new Point(68,80), new Dimension(35,25), 4500, 1000, 11, 20, ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/special moves/hadoken/hdk (1).png")))
				},
				new Animation(43, new int[] {0,3,6,9,11,51},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(0,0), new Dimension(40,96)),
													new Hurtbox(new Point(19,25), new Dimension(24,20))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(11,0), new Dimension(68,41)),
													new Hurtbox(new Point(42,35), new Dimension(41,49)),
													new Hurtbox(new Point(60,77), new Dimension(26,26))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(11,0), new Dimension(68,41)),
													new Hurtbox(new Point(42,35), new Dimension(41,49)),
													new Hurtbox(new Point(60,77), new Dimension(26,26)),
													new Hurtbox(new Point(88,47), new Dimension(56,50))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(11,0), new Dimension(68,41)),
													new Hurtbox(new Point(42,35), new Dimension(41,49)),
													new Hurtbox(new Point(60,77), new Dimension(26,26)),
													new Hurtbox(new Point(96,46), new Dimension(57,50))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(11,0), new Dimension(68,41)),
													new Hurtbox(new Point(42,35), new Dimension(41,49)),
													new Hurtbox(new Point(60,77), new Dimension(26,26))
												})
								},
								new BufferedImage[]
										{
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/special moves/hadoken/hdk (3).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/special moves/hadoken/hdk (4).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/special moves/hadoken/hdk (5).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/special moves/hadoken/hdk (6).png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/special moves/hadoken/hdk (7).png")),
										}),
				50, true);}catch(Exception e){System.out.println("oh no"); return null;}
	}

	@Override
	Attack P7C()
	{
		try{
		return new Attack("Crouch LP",
				new Hitbox[]
				{
						new Hitbox(new Point(32,51), new Dimension(63,21), AttackType.L, 3, 4, 2300, false, false)
				},
				new Animation(27, new int[] {3,7,13},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(8,0), new Dimension(44,60)),
													new Hurtbox(new Point(26,56), new Dimension(25,25))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(8,0), new Dimension(44,60)),
													new Hurtbox(new Point(26,56), new Dimension(25,25))
												})
								},
								new BufferedImage[]
										{
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/lp/lp.png")),
												ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/lp/lp (2).png"))
										}),
				12);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack P8C()
	{
		try{
		return new Attack("Crouch MP",
				new Hitbox[]
				{
						new Hitbox(new Point(32,41), new Dimension(73,29), AttackType.M, 4, 4, 3300, false, false)
				},
				new Animation(28, new int[] {0,4,8,15},
						new Body[]
								{
									new Body(new Hurtbox[]
											{
												new Hurtbox(new Point(8,0), new Dimension(44,60)),
												new Hurtbox(new Point(26,56), new Dimension(25,25))
											}),
									new Body(new Hurtbox[]
											{
												new Hurtbox(new Point(8,0), new Dimension(44,60)),
												new Hurtbox(new Point(26,56), new Dimension(25,25))
											}),
									new Body(new Hurtbox[]
											{
												new Hurtbox(new Point(8,0), new Dimension(44,60)),
												new Hurtbox(new Point(26,56), new Dimension(25,25))
											})
								},
								new BufferedImage[]
										{
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/mp/mp (1).png")),
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/mp/mp (3).png")),
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/mp/mp (2).png"))
										}),
				14);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack P9C()
	{
		try{
		return new Attack("Crouch HP",
				new Hitbox[]
				{
						new Hitbox(new Point(32,64), new Dimension(57,53), AttackType.H, 4, 3, 2150, false, false),
						new Hitbox(new Point(31,71), new Dimension(44,80), AttackType.H, 7, 8, 2150, false, false)
				},
				new Animation(29, new int[] {0,4,7,39},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(7,0), new Dimension(40,74)),
													new Hurtbox(new Point(22,68), new Dimension(25,25))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(16,0), new Dimension(41,100)),
													new Hurtbox(new Point(34,54), new Dimension(41,69))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(12,0), new Dimension(40,100)),
													new Hurtbox(new Point(29,53), new Dimension(41,69))
												})
								},
								new BufferedImage[]
										{
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/hp/hp (1).png")),
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/hp/hp (2).png")),
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/hp/hp (3).png"))
										}),
				38);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack K4C()
	{
		try{
		return new Attack("Crouch LK",
				new Hitbox[]
				{
						new Hitbox(new Point(46,0), new Dimension(67,33), AttackType.L, 4, 4, 2300, false, true)
				},
				new Animation(24, new int[] {4,8,14},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(8,2), new Dimension(72,33)),
													new Hurtbox(new Point(8,31), new Dimension(45,32)),
													new Hurtbox(new Point(26,48), new Dimension(24,25))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(8,2), new Dimension(72,33)),
													new Hurtbox(new Point(8,31), new Dimension(45,32)),
													new Hurtbox(new Point(26,48), new Dimension(24,25))
												})
								},
								new BufferedImage[]
										{
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/lk/lk.png")),
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/lk/lk (2).png"))
										}),
				13);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack K5C()
	{
		try{
		return new Attack("Crouch MK",
				new Hitbox[]
				{
						new Hitbox(new Point(50,0), new Dimension(80,33), AttackType.M, 4, 6, 3300, false, true)
				},
				new Animation(25, new int[] {0,4,10,19},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(5,0), new Dimension(88,32)),
													new Hurtbox(new Point(0,28), new Dimension(45,32)),
													new Hurtbox(new Point(18,54), new Dimension(25,25))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(5,0), new Dimension(88,32)),
													new Hurtbox(new Point(0,28), new Dimension(45,32)),
													new Hurtbox(new Point(18,54), new Dimension(25,25))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(5,0), new Dimension(88,32)),
													new Hurtbox(new Point(0,28), new Dimension(45,32)),
													new Hurtbox(new Point(18,54), new Dimension(25,25))
												})
								},
								new BufferedImage[]
										{
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/mk/mk (1).png")),
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/mk/mk (2).png")),
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/mk/mk (3).png"))
										}),
				18);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack K6C()
	{
		try{
		return new Attack("Crouch HK",
				new Hitbox[]
				{
						new Hitbox(new Point(45,0), new Dimension(87,32), AttackType.H, 4, 6, 4300, true, true)
				},
				new Animation(26, new int[] {0,4,10,18,26,36},
						new Body[]
								{
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(23,0), new Dimension(45,61)),
													new Hurtbox(new Point(41,56), new Dimension(25,25))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(19,0), new Dimension(88,32)),
													new Hurtbox(new Point(23,0), new Dimension(45,61)),
													new Hurtbox(new Point(41,56), new Dimension(25,25))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(19,0), new Dimension(88,32)),
													new Hurtbox(new Point(23,0), new Dimension(45,61)),
													new Hurtbox(new Point(41,56), new Dimension(25,25))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(23,0), new Dimension(45,61)),
													new Hurtbox(new Point(41,56), new Dimension(25,25))
												}),
										new Body(new Hurtbox[]
												{
													new Hurtbox(new Point(23,0), new Dimension(45,61)),
													new Hurtbox(new Point(41,56), new Dimension(25,25))
												})
								},
								new BufferedImage[]
										{
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/hk/hk (1).png")),
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/hk/hk (2).png")),
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/hk/hk (3).png")),
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/hk/hk (4).png")),
											ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/crouching/hk/hk (5).png"))
										}),
				35);}catch(Exception e){System.out.println("oh no"); return null;}
	}

	@Override
	Attack P7A()
	{
		try{
		return new Attack("Jump LP Forward",
				new Hitbox[]
				{
						new Hitbox(new Point(33,36), new Dimension(44,42), AttackType.JL, 2, 42, 2600, false, false)
				},
				new Animation(37, new int[] {0,2,44},
						new Body[]
								{
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(0,0), new Dimension(39,77)),
											new Hurtbox(new Point(13,74), new Dimension(28,24))
										}),
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(0,0), new Dimension(39,77)),
											new Hurtbox(new Point(13,74), new Dimension(28,24))
										})
								},
						new BufferedImage[]
								{
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/lp/lp (2).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/lp/lp (1).png"))
								}),
		43);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack P8A()
	{
		try{
		return new Attack("Jump MP Neutral",
				new Hitbox[]
				{
						new Hitbox(new Point(24,45), new Dimension(63,29), AttackType.JM, 4, 20, 3600, false, false)
				},
				new Animation(38, new int[] {0,4,24,45},
						new Body[]
								{
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(0,-9), new Dimension(40,78)),
											new Hurtbox(new Point(13,87), new Dimension(29,14))
										}),
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(0,-9), new Dimension(40,78)),
											new Hurtbox(new Point(13,87), new Dimension(29,14))
										}),
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(0,-9), new Dimension(40,78)),
											new Hurtbox(new Point(13,87), new Dimension(29,14))
										})
								},
						new BufferedImage[]
								{
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/hpmp/mphp (1).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/hpmp/mphp (2).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/hpmp/mphp (3).png"))
								}),
		44);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack P9A()
	{
		try{
		return new Attack("Jump HP Forward",
				new Hitbox[]
				{
						new Hitbox(new Point(30,23), new Dimension(63,25), AttackType.JH, 4, 8, 4500, false, false)
				},
				new Animation(39, new int[] {0,4,21,44},
						new Body[] {
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(3,27), new Dimension(41,48)),
											new Hurtbox(new Point(16,71), new Dimension(28,23))
										}),
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(3,27), new Dimension(41,48)),
											new Hurtbox(new Point(16,71), new Dimension(28,23))
										}),
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(3,27), new Dimension(41,48)),
											new Hurtbox(new Point(16,71), new Dimension(28,23))
										})
				},
						new BufferedImage[]
								{
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/hpmp/mphp (1).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/hpmp/mphp (2).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/hpmp/mphp (3).png"))
								}),
		43);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack K4A()
	{
		try{
		return new Attack("Jump LK Forward",
				new Hitbox[]
				{
						new Hitbox(new Point(32,-6), new Dimension(40,41), AttackType.JL, 5, 38, 2600, false, false)
				},
				new Animation(34, new int[] {0,3,5,44},
						new Body[]
								{
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(11,10), new Dimension(41,55)),
											new Hurtbox(new Point(24,68), new Dimension(29,24))
										}),
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(11,10), new Dimension(41,55)),
											new Hurtbox(new Point(24,68), new Dimension(29,24))
										}),
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(11,10), new Dimension(41,55)),
											new Hurtbox(new Point(24,68), new Dimension(29,24))
										})
								},
						new BufferedImage[]
								{
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/lk/f lk (1).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/lk/f lk (2).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/lk/f lk (3).png"))
								}),
		43);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack K5A()
	{
		try {
		return new Attack("Jump MK Neutral",
				new Hitbox[]
				{
						new Hitbox(new Point(11,79), new Dimension(79,50), AttackType.JM, 5, 13, 3600, false, false)
				},
				new Animation(35, new int[] {0,5,45},
						new Body[]
								{
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(8,43), new Dimension(40,79)),
											new Hurtbox(new Point(21,119), new Dimension(29,24))
										}),
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(8,43), new Dimension(40,79)),
											new Hurtbox(new Point(21,119), new Dimension(29,24))
										})
								},
						new BufferedImage[]
								{
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/lk/nmklk (2).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/lk/nmklk (3).png"))
								}),
		44);}catch(Exception e){System.out.println("oh no"); return null;}
	}
	@Override
	Attack K6A()
	{
		try {
		return new Attack("Jump HK Forward",
				new Hitbox[]
				{
						new Hitbox(new Point(8,-15), new Dimension(113,50), AttackType.JH, 5, 7, 4300, false, false)
				},
				new Animation(36, new int[] {0,5,12,44},
						new Body[]
								{
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(9,0), new Dimension(39,54)),
											new Hurtbox(new Point(21,50), new Dimension(29,24)),
											new Hurtbox(new Point(46,5), new Dimension(51,39))
											
										}),
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(9,0), new Dimension(39,54)),
											new Hurtbox(new Point(21,50), new Dimension(29,24)),
											new Hurtbox(new Point(46,5), new Dimension(51,39))
										}),
								new Body(new Hurtbox[]
										{
											new Hurtbox(new Point(9,0), new Dimension(39,54)),
											new Hurtbox(new Point(21,50), new Dimension(29,24)),
											new Hurtbox(new Point(46,5), new Dimension(51,39))
										})
								},
						new BufferedImage[]
								{
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/hkmk/fmkhk (3).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/hkmk/fmkhk (4).png")),
									ImageIO.read(new File(System.getProperty("user.dir") + "/characters/ryu/jumping/hkmk/fmkhk (1).png"))
								}),
		43);}catch(Exception e){System.out.println("oh no"); return null;}
	}
}