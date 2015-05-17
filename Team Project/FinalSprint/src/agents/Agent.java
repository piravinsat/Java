package agents;

/**
 * Agent is the abstract class that models an entity represented by a person.
 * <p>
 * People in the supermarket may be either of two types, Customers or Staff
 * members. Each of these types has its unique attributes and methods, but
 * they both fall on the same category. This is the category that Agent is
 * attempting to model.
 * <p>
 * @author Piravin Satkunarajah
 * @author Luigi Pardey
 *
 */
public abstract class Agent {
    /**
     * Class counter for the incremental number of agent identifiers.
     */
    private static long identifier = 0;
    /**
     * The unique number identifier for the agent.
     */
    private long agentID;
    /**
     * The classification of this Agent.
     */
    private AgentType agentType;
    /**
     * Constructs an Agent object with an unique identifier.
     * <p>
     * @param aType the type of agent.
     */
    public Agent(final AgentType aType) {
        agentID = identifier++;
        this.agentType = aType;
    }
    /**
     * Series of actions that this agent performs.
     * <p>
     * @param simTime current time-step.
     */
    public abstract void update(int simTime);
    /**
     * Gives the number that uniquely identifier each agent.
     * <p>
     * @return this agent's ID.
     */
    public final long getAgentID() { return agentID; }
    /**
     * Returns the classification of this Agent.
     * <p>
     * Agents can be either of two types: customers or staff, as given by
     * AgentType.
     * <p>
     * @return the agent type
     * @see AgentType
     */
    public final AgentType getAgentType() {
        return agentType;
    }
}
