package com.wdeath.akka.test;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class WorkingActor extends AbstractActor {

    public JobStart jobStart;
    private float deltaAll = 0, delta = 0;
    private int i = 0;

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
    }

    private void jobStart(JobStart job){
        this.jobStart = job;
        long num = (job.start + job.end) / job.op;

        for(long i = job.start; i <= job.end; i+= num){
            Job job1 = new Job();
            job1.start = i;
            job1.end = i + num;
            ActorRef ref = getContext().system().actorOf(Props.create(JobActor.class));
            ref.tell(job1, this.self());
        }
    }
}
