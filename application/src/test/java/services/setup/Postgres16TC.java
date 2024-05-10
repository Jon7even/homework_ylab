package services.setup;

import org.testcontainers.containers.PostgreSQLContainer;

public class Postgres16TC extends PostgreSQLContainer<Postgres16TC> {
    private static final Postgres16TC postgres = new Postgres16TC();

    private Postgres16TC() {
        super("postgres:16.2-alpine3.19");
    }

    public static Postgres16TC getInstance() {
        return postgres;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
    }
}