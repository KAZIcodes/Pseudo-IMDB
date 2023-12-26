public class ActorRole {
    Actor actor;
    String role;


    public ActorRole(Actor actor, String role) {
        this.actor = actor;
        this.role = role;
    }

    public String getActorRole(){
        return this.actor.getName() + " as " + this.role;
    }

    public String getRole() {
        return role;
    }

    public Actor getActor() {
        return actor;
    }
}
