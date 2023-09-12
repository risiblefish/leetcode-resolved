//package ih.dmage.domain.service;
//
//import ih.dmage.domain.entity.InBattleModel;
//import ih.dmage.domain.entity.Weapon;
//import ih.dmage.domain.primitive.SkillSnippet;
//import ih.dmage.domain.primitive.debuff.dot.Dot;
//import ih.dmage.domain.primitive.debuff.dot.DotType;
//
//import java.util.List;
//import java.util.Random;
//
///**
// * 只针对远征的战斗系统
// */
//public class ExpeditionBattleSystem {
//    int round;
//    final int ROUND_LIMIT = 15;
//    boolean isBattleEnd;
//    final Random RAND = new Random();
//    List<InBattleModel> heroes;
//    InBattleModel boss;
//
//    public void battleWithRoundLimit(List<InBattleModel> heroes, InBattleModel monster){
//        init(heroes, monster);
//        while(round++ <= 15){
//            heroes.forEach( h -> {
//
//            });
//
//        }
//        System.out.println("battle end");
//    }
//
//    private void heroAttack(InBattleModel hero){
//        if(hero.getEnergy() >= 100 && !hero.isSilent()){
//             boss.charge(20);
//             heroEnergySkillAttack(hero);
//        }else{
//            if(!hero.isFear()){
//                boss.charge(10);
//            }
//        }
//    }
//
//    private void heroEnergySkillAttack(InBattleModel hero){
//        for (SkillSnippet snippet : hero.getSkill().getSkillSnippets()) {
//
//        }
//    }
//
//    private void init(List<InBattleModel> heroes, InBattleModel boss){
//        this.heroes = heroes;
//        this.boss = boss;
//        round = 1;
//    }
//
//    private void releaseEnergySkill(InBattleModel hero, int skillId){
//        //能量清空
//        hero.releaseEnergy();
//        //如果带了铃铛，则充能
//        if(hero.getWeapon() == Weapon.DEMON_BELL){
//            releaseDemonBell(hero);
//        }
//        //释放具体技能
//        switch (skillId){
//            case 1: releaseEnergySkillQueen(hero);
//        }
//    }
//
//
//    private void releaseDemonBell(InBattleModel hero){
//        //充能20，有50%概率再充10
//        int val = RAND.nextInt(2) == 0 ? 20 : 30;
//        heroes.stream().filter(h -> h != hero).forEach( h -> h.receiveEnergy(val));
//    }
//
//
//    private void releaseEnergySkillQueen(InBattleModel hero){
//        //给boss上dot
//        Dot dot = Dot.builder().dotType(DotType.BLEED).ratio(calculateTrueDamage(hero, 12)).round(2).build();
//        boss.addDot(dot);
//        //给boss上一层深渊领域
//        boss.addOneAbyssDomain();
//        //有25%几率再上一层深渊领域
//        if(RAND.nextInt(4) == 0){
//            boss.addOneAbyssDomain();
//        }
//        //降低boss10%暴击率2回合
//        boss.reduceCritChance(0.1, 2);
//    }
//
//
//    private int calculateTrueDamage(InBattleModel model, int val){
//        //todo
//        return 1;
//    }
//
//
//    private void roundEndFlush(InBattleModel model){
//        model.roundEndFlush();
//    }
//}
