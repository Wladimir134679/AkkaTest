package com.wdeath.akka.test;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class WorkingActor extends AbstractActor {

    public JobStart jobStart;
    private float deltaAll = 0, delta = 9;
    private int i = 0;
    private long timeLast = 0, timeCurrent;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(JobStart.class, this::jobStart)
                .match(JobResult.class, this::result)
                .build();
    }

    private void result(JobResult result){
        System.out.println("Result: " + result.i + " : res = " + result.res);
        System.out.println("Delta: " + result.timeJob);
        i++;
        delta += result.timeJob;
        deltaAll = delta / (float)i;
        System.out.println("DeltaAll: " + deltaAll);
//        if(timeLast == 0)
//            timeLast = System.currentTimeMillis();
//        timeCurrent = System.currentTimeMillis();
//        timeLast = System.currentTimeMillis();
//        delta = (timeCurrent - timeLast);
//        i ++;
//        if(deltaAll == 0)
//            deltaAll = delta;
//        deltaAll = (deltaAll + delta) / 2f;
    }

    private void jobStart(JobStart job){
        this.jobStart = job;
        long num = (job.start + job.end) / job.op;

        for(long i = job.start; i <= job.end; i+= num){
            Job job1 = new Job();
            job1.s = i;
            job1.e = i + num;
            ActorRef ref = getContext().system().actorOf(Props.create(JobActor.class));
            ref.tell(job1, this.self());
        }
    }
}
