package org.jboss.dna.graph.connector.path;

import org.jboss.dna.graph.ExecutionContext;
import org.jboss.dna.graph.connector.LockFailedException;
import org.jboss.dna.graph.property.Path;
import org.jboss.dna.graph.query.QueryResults;
import org.jboss.dna.graph.request.AccessQueryRequest;
import org.jboss.dna.graph.request.LockBranchRequest.LockScope;

public interface PathWorkspace {
    /**
     * Returns the name of the workspace. There can only be one workspace with a given name per repository.
     * 
     * @return the name of the workspace
     */
    String getName();

    /**
     * Returns the root node in the workspace. This returns a {@link PathNode map node} where {@code node.getParent() == null} and
     * {@code node.getPath().isRoot() == true}.
     * 
     * @return the root node in the workspace
     */
    PathNode getRoot();

    /**
     * Returns the node at the given path, if one exists of {@code null} if no {@PathNode node} exists at the given
     * path.
     * 
     * @param path the path of the node to retrieve; may not be null
     * @return the node at the given path, if one exists of {@code null} if no {@PathNode node} exists at the given
     *         path.
     */
    PathNode getNode( Path path );

    /**
     * Attempts to lock the given node with the given timeout. If the lock attempt fails, a {@link LockFailedException} will be
     * thrown.
     * 
     * @param node the node to be locked; may not be null
     * @param lockScope the scope of the lock (i.e., whether descendants of {@code node} should be included in the lock
     * @param lockTimeoutInMillis the maximum lifetime of the lock in milliseconds; zero (0) indicates that the connector default
     *        should be used
     * @throws LockFailedException if the implementing connector supports locking but the lock could not be acquired.
     */
    void lockNode( PathNode node,
                   LockScope lockScope,
                   long lockTimeoutInMillis ) throws LockFailedException;

    /**
     * Attempts to unlock the given node.
     * 
     * @param node the node to be unlocked; may not be null
     */
    void unlockNode( PathNode node );

    /**
     * Find the lowest existing node along the path.
     * 
     * @param path the path to the node; may not be null
     * @return the lowest existing node along the path, or the root node if no node exists on the path
     */
    Path getLowestExistingPath( Path path );

    /**
     * Perform a query of this workspace.
     * 
     * @param context the context in which the query is to be executed; may not be null
     * @param accessQuery the access query; may not be null
     * @return the query results, or null if the query is not supported
     */
    QueryResults query( ExecutionContext context,
                        AccessQueryRequest accessQuery );

    /**
     * Perform a full-text search of this workspace.
     * 
     * @param context the context in which the query is to be executed; may not be null
     * @param fullTextSearchExpression the full-text search expression; may not be null
     * @return the query results, or null if the query is not supported
     */
    QueryResults search( ExecutionContext context,
                         String fullTextSearchExpression );

}