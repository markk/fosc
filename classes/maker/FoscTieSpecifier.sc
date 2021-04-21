/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscTieSpecifier


SUMMARY:: Returns a FoscTieSpecifier.


DESCRIPTION:: TODO


USAGE::

'''

• FoscTieSpecifier

!!!TODO: needs to be updated

Tie specifier.
'''
------------------------------------------------------------------------------------------------------------ */
FoscTieSpecifier : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <stripTies, <tieAcrossDivisions, <tieConsecutiveNotes;
    *new { |stripTies=false, tieAcrossDivisions=false, tieConsecutiveNotes=false|
        if (stripTies && tieConsecutiveNotes) {
            throw("FoscTieSpecifier:new: can't tie leaves and strip ties at the same time.");
        };
        ^super.new.init(stripTies, tieAcrossDivisions, tieConsecutiveNotes);
    }
    init { |argStripTies, argTieAcrossDivisions, argTieConsecutiveNotes|
        stripTies = argStripTies;
        tieAcrossDivisions = argTieAcrossDivisions;
        tieConsecutiveNotes = argTieConsecutiveNotes;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: SPECIAL METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • value

    Calls tie specifier on divisions.
    '''
    -------------------------------------------------------------------------------------------------------- */
    value { |divisions|
        this.prTieAcrossDivisions(divisions);
        this.prTieConsecutiveNotes(divisions);
        this.prStripTies(divisions);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prStripTies

    • Without tie specifier

    code::
    a = FoscTupletMaker();
    a.(divisions: [2/16, 3/16, 5/32], tupletRatios: [[2, 1.0], [3, 2.0], [4, 3]]);
    a.show;

    img:: ![](../img/maker-tie-specifier-1.png)
    '''

    p = "%/fosc/docs/img/maker-tie-specifier-1".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    • With tie specifier
    
    code::
    m = FoscTieSpecifier(stripTies: true);
    a = FoscTupletMaker(tieSpecifier: m);
    a.(divisions: [2/16, 3/16, 5/32], tupletRatios: [[2, 1.0], [3, 2.0], [4, 3]]);
    a.show;

    img:: ![](../img/maker-tie-specifier-2.png)
    '''

    p = "%/fosc/docs/img/maker-tie-specifier-2".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    prStripTies { |divisions|
        if (stripTies) {
            divisions.do { |division|
                FoscIteration(division).leaves.do { |leaf| leaf.detach(FoscTie) };
            };
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prTieAcrossDivisions

    
    • Without ties across divisions

    code::
    a = FoscTupletMaker();
    a.(divisions: [2/16, 3/16, 5/32], tupletRatios: [[2, 1], [3, 2], [4, 3]]);
    a.show;

    img:: ![](../img/maker-tie-specifier-3.png)
    '''

    p = "%/fosc/docs/img/maker-tie-specifier-3".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    • With ties across divisions
    
    code::
    m = FoscTieSpecifier(tieAcrossDivisions: true);
    a = FoscTupletMaker(tieSpecifier: m);
    a.(divisions: [2/16, 3/16, 5/32], tupletRatios: [[2, 1], [3, 2], [4, 3]]);
    a.show;

    img:: ![](../img/maker-tie-specifier-4.png)
    '''

    p = "%/fosc/docs/img/maker-tie-specifier-4".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    prTieAcrossDivisions { |divisions|
        var length, restPrototype, leafA, leafB, leaves, continue, pitchedPrototype, logicalTieA, logicalTieB;
        var combinedLogicalTie, tie;

        if (this.tieAcrossDivisions.not) { ^nil };
        if (this.stripTies) { ^nil };
        if (this.tieConsecutiveNotes) { ^nil };
        length = divisions.size;
        restPrototype = [FoscRest, FoscMultimeasureRest];

        divisions.doAdjacentPairs { |a, b, i|
            leafA = FoscIteration(a).leaves.all.last;
            leafB = FoscIteration(b).leaves.next;
            leaves = [leafA, leafB];
            pitchedPrototype = [FoscNote, FoscChord];
            continue = leaves.every { |leaf| pitchedPrototype.includes(leaf.class) };
            if (continue) {
                logicalTieA = leafA.prGetLogicalTie;
                logicalTieB = leafB.prGetLogicalTie;
                if (logicalTieA == logicalTieB) { continue = false };
            };
            if (continue) {
                combinedLogicalTie = FoscLogicalTie([logicalTieA, logicalTieB]).flat;
                combinedLogicalTie.do { |leaf| leaf.detach(FoscTie) };
                tie = FoscTie();
                tie.prUnconstrainContiguity;
                if (tie.prAttachmentTestAll(combinedLogicalTie)) { combinedLogicalTie.attach(tie) };
                tie.prConstrainContiguity;
            };
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prTieConsecutiveNotes
    
    
    • Without ties across consecutive notes

    code::
    a = FoscTupletMaker();
    a.(divisions: [2/16, 3/16, 5/32], tupletRatios: [[2, 1], [3, 2], [4, 3]]);
    a.show;

    img:: ![](../img/maker-tie-specifier-5.png)
    '''

    p = "%/fosc/docs/img/maker-tie-specifier-5".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    • With ties across consecutive notes
    
    code::
    m = FoscTieSpecifier(tieConsecutiveNotes: true);
    a = FoscTupletMaker(tieSpecifier: m);
    a.(divisions: [2/16, 3/16, 5/32], tupletRatios: [[2, 1], [3, 2, 4, 1], [4, 3]]);
    a.show;

    img:: ![](../img/maker-tie-specifier-6.png)
    '''

    p = "%/fosc/docs/img/maker-tie-specifier-6".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    • With ties across consecutive notes
    
    code::
    a = FoscTupletMaker();
    b = a.(divisions: [2/16, 3/16, 5/32], tupletRatios: [[2, 1], [3, 2, 4, 1], [4, 3]]);
    FoscPitchSpecifier().(b, [60, 60, 60, 61, 62, [60, 64], [60, 64], 67]);
    FoscTieSpecifier(tieConsecutiveNotes: true).(b);
    a.show;

    img:: ![](../img/maker-tie-specifier-7.png)
    '''

    p = "%/fosc/docs/img/maker-tie-specifier-7".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    prTieConsecutiveNotes { |divisions|
        var leaves, groups, subgroups;
        if (this.tieConsecutiveNotes.not) { ^nil };
        leaves = FoscSelection(divisions).leaves;
        leaves.do { |leaf| leaf.detach(FoscTie) };
        groups = leaves.separate { |a, b| a.class != b.class };
        groups = groups.select { |list| list[0].isPitched };
        groups.do { |group|
            case 
            { group[0].isKindOf(FoscNote) } {
                subgroups = group.separate { |a, b| a.writtenPitch != b.writtenPitch };
            }
            { group[0].isKindOf(FoscChord) } {
                subgroups = group.separate { |a, b| a.writtenPitches != b.writtenPitches };
            };
            subgroups.do { |subgroup|
                if (subgroup.size > 1) { FoscAttach(subgroup, FoscTie()) };
            };
        };
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stripTies

    Is true when rhythm-maker should strip all ties from all leaves in each division.

    Set to true or false.
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tieAcrossDivisions

    Is true when rhythm maker should tie across divisons. Otherwise false.

    Set to true or false.
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tieConsecutiveNotes

    Is true when rhythm-maker should tie consecutive notes. Otherwise false.

    Set to true or false.
    '''
    -------------------------------------------------------------------------------------------------------- */
}
