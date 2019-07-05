/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    @Test
    public void testModifyVertices() {
        Graph<String> graph = emptyInstance();
        Set<String> labels = new TreeSet<>();
        labels.add("a");
        labels.add("b");
        labels.add("c");
        for (String label : labels) {
            graph.add(label);
        }
        
        assertEquals("expected vertices set is same with labels", labels, graph.vertices());
        
        for (String label : labels) {
            graph.remove(label);
        }
        
        assertEquals("expected no vertices after removing", Collections.emptySet(), graph.vertices());
    }
    
    @Test
    public void testSetEdges() {
        Graph<String> graph = emptyInstance();
        Set<String> labels = new TreeSet<>();
        labels.add("a");
        labels.add("b");
        labels.add("c");
        for (String label : labels) {
            graph.add(label);
        }
        
        graph.set("a", "b", 1);
        graph.set("b", "c", 2);
        graph.set("a", "c", 3);
        
        Map<String, Integer> sources = graph.sources("c");
        assertEquals(3, (long)sources.get("a"));
        assertEquals(2, (long)sources.get("b"));
        
        Map<String, Integer> targets = graph.targets("a");
        
        assertEquals(1, (long)targets.get("b"));
        assertEquals(3, (long)sources.get("c"));
    }
}
