package vu.astar

import org.junit.Test


/**
 * @author v.uspenskiy
 * @since 21.10.11 0:59
 */
class AStarPathSearchTest {

    @Test def testHandle() {

        lazy val from = new Node("from")
        lazy val intermediate1 = new Node("intermediate1")
        lazy val intermediate2 = new Node("intermediate2")
        lazy val intermediate3 = new Node("intermediate3")
        lazy val to = new Node("to")

        from.getAdjancedNodes.add(intermediate1)
        from.getAdjancedNodes.add(intermediate2)
        from.getAdjancedNodes.add(intermediate3)

        intermediate1.getAdjancedNodes.add(from)
        intermediate1.getAdjancedNodes.add(to)
        intermediate2.getAdjancedNodes.add(from)
        intermediate2.getAdjancedNodes.add(to)
        intermediate3.getAdjancedNodes.add(from)
        intermediate3.getAdjancedNodes.add(to)

        to.getAdjancedNodes.add(intermediate1)
        to.getAdjancedNodes.add(intermediate2)
        to.getAdjancedNodes.add(intermediate3)

        val f = (node1: Node, node2: Node) => {
            if("intermediate3" == node1.name) {
                0.50
            } else {
                1.00
            }
        }

        Console.println(AStarPathSearch.searchForPath(from, to, f).map(_.mkString(" -> ")).getOrElse("No path"))
    }
}