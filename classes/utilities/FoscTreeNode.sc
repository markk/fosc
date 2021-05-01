/* -------------------------------------------------------------------------------------------------------------

TITLE:: FoscTreeNode


SUMMARY:: Returns a FoscTreeNode.


DESCRIPTION:: A tree node.


USAGE::
------------------------------------------------------------------------------------------------------------- */
FoscTreeNode : FoscObject {
    var <name, <parent;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    *new { |name|
        ^super.new.init(name);
    }
    init { |argName|
        if (argName.notNil) { name = argName.asSymbol };
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    prSetParent { |argParent|
        parent = argParent;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • depth
    -------------------------------------------------------------------------------------------------------- */
    depth {
        if (parent.isNil) { ^0 };
        ^this.properParentage.size;
    }
    /* --------------------------------------------------------------------------------------------------------
    • depthwiseInventory

    A dictionary of all nodes in a rhythm-tree, organized by their depth relative the root node.

    Returns dictionary.

    '''
    code::
    a = FoscTreeContainer(name: 'a');
    b = FoscTreeContainer(name: 'b');
    c = FoscTreeContainer(name: 'c');
    d = FoscTreeContainer(name: 'd');
    e = FoscTreeContainer(name: 'e');
    f = FoscTreeContainer(name: 'f');
    g = FoscTreeContainer(name: 'g');

    a.addAll([b, c]);
    b.addAll([d, e]);
    c.addAll([f, g]);

    i = a.depthwiseInventory;
    k = i.keys.as(Array).sort;
    o = [];
    k.do { |depth, items|
        o = o.add("depth: %".format(depth));
        i[depth].do { |node|
            o = o.add("%%".format("".padLeft(depth, "\t"), node.name));
        };
    };
    o.join("\n");
    '''
    -------------------------------------------------------------------------------------------------------- */
    depthwiseInventory {
        var recurse, inventory;
        recurse = { |node|
            if (inventory.keys.includes(node.depth).not) { inventory[node.depth] = [] };
            inventory[node.depth] = inventory[node.depth].add(node);
            if (node.respondsTo('items') && { node.items.notNil }) {
                node.items.do { |child| recurse.(child) };
            };
        };
        inventory = ();
        recurse.(this);
        ^inventory;
    }
    /* --------------------------------------------------------------------------------------------------------
    • index
    -------------------------------------------------------------------------------------------------------- */
    index {
        ^this.root.nodes.do { |node, i| if (this == node) { ^i } };
    }
    /* --------------------------------------------------------------------------------------------------------
    • improperParentage

    The improper parentage of a node in a rhythm-tree, being the sequence of node beginning with itself and ending with the root node of the tree.

    Returns array of tree nodes.

    '''
    code::
    a = FoscTreeContainer();
    b = FoscTreeContainer();
    c = FoscTreeNode();
    a.add(b);
    b.add(c);

    a.improperParentage == [a];         // true

    code::
    b.improperParentage == [b, a];      // true

    code::
    c.improperParentage == [c, b, a];   // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    improperParentage {
        var node, parentage;
        node = this;
        parentage = [node];
        while { node.parent.notNil } {
            node = node.parent;
            parentage = parentage.add(node);
        };
        ^parentage;
    }
    /* --------------------------------------------------------------------------------------------------------
    • isRoot
    -------------------------------------------------------------------------------------------------------- */
    isRoot {
        ^parent.isNil;
    }
    /* --------------------------------------------------------------------------------------------------------
    • properParentage

    The proper parentage of a node in a rhythm-tree, being the sequence of node beginning with the node's immediate parent and ending with the root node of the tree.

    Returns array of tree nodes.

    '''
    code::
    a = FoscTreeContainer();
    b = FoscTreeContainer();
    c = FoscTreeNode();
    a.add(b);
    b.add(c);

    a.properParentage == [];            // true

    code::
    b.properParentage == [a];           // true

    code::
    c.properParentage == [b, a];        // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    properParentage {
        ^this.improperParentage[1..];
    }
    /* --------------------------------------------------------------------------------------------------------
    • root
    -------------------------------------------------------------------------------------------------------- */
    root {
        var node;
        node = this;
        while { node.parent.notNil } { node = node.parent };
        ^node
    }
    /* --------------------------------------------------------------------------------------------------------
    • siblings
    -------------------------------------------------------------------------------------------------------- */
    siblings { |includeSelf=false|
        var siblings;
        if (this.isRoot) { ^nil };
        siblings = this.parent.children;
        if (includeSelf.not) { siblings.remove(this) };
        ^siblings;
    }
}
