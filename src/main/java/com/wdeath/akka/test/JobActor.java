package com.wdeath.akka.test;

import akka.actor.AbstractActor;

public class JobActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Job.class, this::job)
                .build();
    }

    private void job(Job job){
        long timeStart = System.currentTimeMillis();
        JobResult result = new JobResult();
        result.i = job.s;
        for(long i = job.s; i < job.e; i++){
            result.res = result.res + (float)Math.sin(Math.cos(i / Math.PI));
        }
        long delta = System.currentTimeMillis() - timeStart;
        result.timeJob = delta;
        sender().tell(result, this.self());
    }
}
