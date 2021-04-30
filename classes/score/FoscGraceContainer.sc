/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscGraceContainer


SUMMARY:: Returns a FoscGraceContainer.


DESCRIPTION:: Grace container.

LilyPond formats grace notes with neither a slash nor a slur.

Fill grace containers with notes, rests or chords.

Attach grace containers to notes, rests or chords.


USAGE::

'''
Grace notes.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
c = FoscGraceContainer([FoscNote(60, 1/16), FoscNote(62, 1/16)]);
c[0].attach(FoscArticulation('>'));
a[2].attach(c);
a.show;
'''

'''
Slashed grace note.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
c = FoscGraceContainer([FoscNote(60, 1/8)], slashed: true);
c[0].attach(FoscArticulation('>'));
a[2].attach(c);
a.show;
'''

'''
Slurred grace note.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
c = FoscGraceContainer([FoscNote(60, 1/8)], slurred: true);
c[0].attach(FoscArticulation('>'));
a[2].attach(c);
a.show;
'''

'''
Slashed and slurred grace note.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
c = FoscGraceContainer([FoscNote(60, 1/8)], slashed: true, slurred: true);
c[0].attach(FoscArticulation('>'));
a[2].attach(c);
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
FoscGraceContainer : FoscContainer {
    var <mainLeaf, <slashed, <slurred;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    *new { |components, slashed=false, slurred=false, tag|
        ^super.new(components, tag: tag).initFoscGraceContainer(slashed, slurred);
    }
    initFoscGraceContainer { |argSlashed, argSlurred|
        slashed = argSlashed;
        slurred = argSlurred;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prAttach
    -------------------------------------------------------------------------------------------------------- */
    prAttach { |leaf|
        if (leaf.isKindOf(FoscLeaf).not) {
            throw("%:%: must attach to leaf: %.".format(this.species, thisMethod.name, leaf));
        };
        leaf.instVarPut('graceContainer', this);
        mainLeaf = leaf;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prDetach
    -------------------------------------------------------------------------------------------------------- */
    prDetach { |leaf|
        var localCarrier;
        if (mainLeaf.notNil) {
            localCarrier = mainLeaf;
            localCarrier.instVarPut('graceContainer', nil);
            mainLeaf = nil;
        };
        ^this;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prFormatOpenBracketsSlot
    -------------------------------------------------------------------------------------------------------- */
    prFormatOpenBracketsSlot { |bundle|
        var result, string;
        result = [];
        string = case
        { slashed && slurred.not } { "\\slashedGrace" }
        { slashed.not && slurred } { "\\appoggiatura" }
        { slashed && slurred } { "\\acciaccatura" }
        { slashed.not && slurred.not } { "\\grace" }
        { "\\grace" };
        result = result.add([['graceBrackets', 'open'], ["% {".format(string)]]);
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormat
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        this.prUpdateNow(indicators: true);
        ^this.prFormatComponent;
    }
}
