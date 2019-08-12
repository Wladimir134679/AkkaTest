package com.wdeath.akka.test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class LauncherAkka {

    public static void main(String[] args) {
        System.out.println("Start");

        ActorSystem actorSystem = ActorSystem.create("actorSystem");
        ActorRef ref = actorSystem.actorOf(Props.create(WorkingActor.class));

        JobStart job = new JobStart();
        job.start = 0;
        job.end = 1_000_000;
        job.op = 1000_000;

        ref.tell(job, null);
    }
}
