package vu.astar

import collection.mutable.HashSet


/**
 * @author v.uspenskiy
 * @since 13.11.11 23:46
 */

class Node(val name: String, adjancedNodes: HashSet[Node] = new HashSet[Node]()) {
    def getAdjancedNodes = adjancedNodes
    override def toString = name
}

object AStarPathSearch {

    private case class FrontierItem(node: Node, previousItem: Option[FrontierItem])
    

    def searchForPath(from: Node, to: Node,
                   f: ((Node, Node) => Double)): Option[List[Node]] = {

        var frontier = Set[FrontierItem]()
        var nextExplored = FrontierItem(from, None)

        while(nextExplored.node != to) {
            frontier = frontier ++ newFrontierItems(nextExplored, frontier)
            if(frontier.isEmpty) {
                return None
            }

            nextExplored = frontier.min(Ordering.by(nodeCost(_: FrontierItem, f)))
            frontier = frontier - nextExplored
        }

        Some(pathFor(nextExplored));
    }

    private def newFrontierItems(nextExplored: FrontierItem,
                                 frontier: Set[FrontierItem]): HashSet[FrontierItem] = {

        nextExplored.node.getAdjancedNodes
            .map(FrontierItem(_, Some(nextExplored)))
            .filter(c => frontier.forall(f => c.node != f.node))
    }

    private def nodeCost(frontierItem: FrontierItem, f: ((Node, Node) => Double)): Double = {
        frontierItem.previousItem.map({ item =>
                f(frontierItem.node, item.node) + nodeCost(item, f)
            }).getOrElse(0.00)
    }

    private def pathFor(nextExplored: FrontierItem): List[Node] = {
        nextExplored.previousItem.map(pathFor(_)).getOrElse(List()) ++ List(nextExplored.node)
    }
}