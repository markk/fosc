/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscParentage


SUMMARY:: Returns a FoscParentage.


DESCRIPTION:: Parentage of a component.

!!!TODO: incomplete


USAGE::

'''
code::
a = FoscNote(60, 1/4);
b = FoscVoice([a]);
p = FoscParentage(a);
p.components;
'''
------------------------------------------------------------------------------------------------------------ */
FoscParentage : FoscSequence {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <component;
    *new { |component, graceNotes=false|
        assert([FoscComponent, Nil].any { |type| component.isKindOf(type) });
        ^super.new.init(component, graceNotes);
    }
    init { |argComponent, graceNotes|
        var parent, prototype;
        component = argComponent;
        items = [];
        if (component.notNil) {
            parent = component;
            prototype = [FoscAfterGraceContainer, FoscGraceContainer];
            while { parent.notNil } {
                items = items.add(parent);
                if (graceNotes && { prototype.any { |type| parent.isKindOf(type) } }) {
                    parent = parent.mainLeaf;
                } {
                    parent = parent.parent;
                };
            };
        };
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • component

    The component from which the selection was derived.

    Returns component.

    '''
    code::
    a = FoscNote(60, 1/4);
    b = FoscVoice([a]);
    p = FoscParentage(a);
    p.component == a;   // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • components

    Gets components.

    Returns array.

    '''
    code::
    a = FoscNote(60, 1/4);
    b = FoscVoice([a]);
    p = FoscParentage(a);
    p.components;
    '''
    -------------------------------------------------------------------------------------------------------- */
    components {
        ^this.items;
    }
    /* --------------------------------------------------------------------------------------------------------
    • depth

    Length of proper parentage of component.

    Returns integer.

    '''
    FIXME ERROR: FoscRhythm::new: bad value: FoscNote('C4', 1/1).

    code::nointerpret
    a = FoscNote(60, 1);
    b = FoscContainer([FoscRhythm([3, 4], [a, 2, 1])]);
    p = FoscParentage(a);
    p.depth;
    '''
    -------------------------------------------------------------------------------------------------------- */
    // DEPRECATED
    // depth {
    //     ^items[1..].size;
    // }
    /* --------------------------------------------------------------------------------------------------------
    • isOrphan

    Is true when component has no parent. Otherwise false.

    Returns true or false.

    '''
    code::
    a = FoscNote(60, 1);
    b = FoscVoice([a]);
    p = FoscParentage(a);
    p.isOrphan;         // false

    code::
    a = FoscNote(60, 1);
    p = FoscParentage(a);
    p.isOrphan;         // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    isOrphan {
       ^this.parent.isNil;
    }
    /* --------------------------------------------------------------------------------------------------------
    • parent

    Gets parent. Returns nil when component has no parent.

    Returns component or nil.

    '''
    code::
    a = FoscNote(60, 1);
    b = FoscVoice([a]);
    p = FoscParentage(a);
    p.parent === b;     // true

    code::
    a = FoscNote(60, 1);
    p = FoscParentage(a);
    p.parent;           // nil
    '''
    -------------------------------------------------------------------------------------------------------- */
    parent {
        if (items.size > 1) { ^items[1] } { ^nil };
    }
    /* --------------------------------------------------------------------------------------------------------
    • prolation

    Gets prolation.

    Returns multiplier.

    '''
    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    a[0].prGetParentage.prolation.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    // abjad 3.0
    prolation {
        var prolations, product;
        prolations = [FoscMultiplier(1)] ++ this.prProlations;
        product = prolations.reduce('*');
        ^product;
    }
    /* --------------------------------------------------------------------------------------------------------
    • root

    Root is last component in parentage.

    Returns component.

    '''
    code::
    a = FoscNote(60, 1);
    b = FoscVoice([a]);
    p = FoscParentage(a);
    p.root == b;
    '''
    -------------------------------------------------------------------------------------------------------- */
    root {
        ^items[items.lastIndex];
    }
    /* --------------------------------------------------------------------------------------------------------
    • scoreIndex

    Gets score index.

    Returns array of zero or more nonnegative integers.

    '''
    code::
    a = FoscNote(60, 1);
    b = FoscScore([FoscStaff([FoscVoice([a])])]);
    p = FoscParentage(a);
    p.scoreIndex;

    code::
    a.prGetParentage.scoreIndex
    '''
    -------------------------------------------------------------------------------------------------------- */
    // DEPRECATED
    scoreIndex {
        var result, current, index;
        result = [];
        current = this[0];
        this[1..].do { |parent|
            index = parent.indexOf(current);
            result = result.insert(0, index);
            current = parent;
        };
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    • tupletDepth

    DEPRECATED

    '''
    code::nointerpret
    a = FoscNote(60, 1);
    b = FoscRhythm([3, 4], [1, FoscRhythm(3, [1, 2, a])]);
    p = FoscParentage(a);
    p.tupletDepth;
    '''
    -------------------------------------------------------------------------------------------------------- */
    // DEPRECATED
    // tupletDepth {
    //     var result;
    //     result = 0;
    //     items[1..].do { |parent|
    //         if (parent.isKindOf(FoscRhythm) && { parent.isTuplet }) { result = result + 1 };
    //     };
    //     ^result;
    // }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • firstInstanceOf (abjad: get_first)

    Gets first instance of prototype in parentage.

    Returns component or none.

    '''
    FIXME ERROR: Message 'add' not understood.

    code::nointerpret
    a = FoscNote(60, 1);
    b = FoscContainer([c = FoscRhythm([3, 4], [a, 2, 1])]);
    p = FoscParentage(a);

    p.firstInstanceOf(FoscNote) === a;          // true

    code::nointerpret
    p.firstInstanceOf(FoscRhythm) === c;        // true

    code::nointerpret
    p.firstInstanceOf(FoscContainer) === b;     // true

    code::nointerpret
    p.firstInstanceOf(FoscRest);                // nil
    '''
    -------------------------------------------------------------------------------------------------------- */
    firstInstanceOf { |prototype|
        if (prototype.isNil) { prototype = FoscComponent };
        if (prototype.isSequenceableCollection.not) { prototype = [prototype] };
        items.do { |component|
            if (prototype.any { |type| component.isKindOf(type) }) { ^component };
        };
        ^nil;
    }
    /* --------------------------------------------------------------------------------------------------------
    • logicalVoice

    Gets logical voice of items.

    '''
    code::
    a = FoscLeafMaker().(#[60,62,64,65], [1/4]);
    b = FoscVoice(a);
    c = FoscStaff([b], name: 'bar');
    d = FoscScore([c], name: 'foo');
    p = FoscParentage(b[0]);
    l = p.logicalVoice;
    '''
    -------------------------------------------------------------------------------------------------------- */
    logicalVoice {
        var keys, logicalVoice;
        keys = #['score', 'staff_group', 'staff', 'voice'];
        logicalVoice = ();
        keys.do { |key| logicalVoice[key] = "" };
        items.do { |component|
            case
            { component.isKindOf(FoscVoice) } {
                if (logicalVoice['voice'].isEmpty) {
                    logicalVoice['voice'] = FoscParentage.prIDString(component);
                };
            }
            { component.isKindOf(FoscStaff) } {
                if (logicalVoice['staff'].isEmpty) {
                    logicalVoice['staff'] = FoscParentage.prIDString(component);
                    // explicit staff demands a nested voice
                    // if no explicit voice has been found
                    // create implicit voice here with random integer
                    if (logicalVoice['voice'].isEmpty) {
                        logicalVoice['voice'] = component.identityHash;
                    };
                };
            }
            { component.isKindOf(FoscStaffGroup) } {
                if (logicalVoice['staff_group'].isEmpty) {
                    logicalVoice['staff_group'] = FoscParentage.prIDString(component);
                };
            }
            { component.isKindOf(FoscScore) } {
                if (logicalVoice['score'].isEmpty) {
                    logicalVoice['score'] = FoscParentage.prIDString(component);
                };
            };
        };
        ^logicalVoice;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE CLASS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prIDString

    '''
    code::
    a = FoscNote(60, 1);
    b = FoscVoice([a]);
    p = FoscParentage(b);
    FoscParentage.prIDString(p.component);

    code::
    a = FoscNote(60, 1);
    a = FoscVoice(a).name_('voice_1');
    p = FoscParentage(a);
    FoscParentage.prIDString(p.component);
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prIDString { |component|
        var lhs, rhs;
        lhs = component.species.name;
        if (component.respondsTo('name') && { component.name.notNil }) {
            rhs = component.name;
        } {
            rhs = component.identityHash;
        };
        ^"%-%".format(lhs, rhs);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prProlations

    '''
    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    p = FoscParentage(a[0]);
    p.prProlations.do { |e| [e, e.str].postln };
    '''
    -------------------------------------------------------------------------------------------------------- */
    prProlations {
        var prolations, default, prolation;
        prolations = [];
        default = FoscMultiplier(1);
        this.do { |parent|
            if (parent.respondsTo('impliedProlation')) {
                prolation = parent.impliedProlation;
            } {
                prolation = default;
            };
            prolations = prolations.add(prolation);
        };
        ^prolations;
    }
}
