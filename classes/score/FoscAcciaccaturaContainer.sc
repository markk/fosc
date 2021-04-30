/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscAcciaccaturaContainer


SUMMARY:: Returns a FoscAcciaccaturaContainer.


DESCRIPTION:: Acciaccatura container.

Acciaccaturas are played before the beat.

LilyPond positions acciaccaturas immediately before main notes.

LilyPond formats one-note acciaccaturas with a slashed stem and a slur.

NOTE: LilyPond fails to format multinote acciaccaturas with a slashed stem. This means that multinote acciaccaturas look exactly like appoggiaturas.

USAGE::

'''
Acciaccatura note.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
c = FoscAcciaccaturaContainer([FoscNote(60, 1/8)]);
c[0].attach(FoscArticulation('>'));
a[2].attach(c);
a.show;
'''

'''
Acciaccatura notes.

code::
a = FoscStaff(FoscLeafMaker().("C4 D4 E4 F4", [1/4]));
c = FoscAcciaccaturaContainer([FoscNote(60, 1/16), FoscNote(62, 1/16)]);
c[0].attach(FoscArticulation('>'));
a[2].attach(c);
a.format;

code::
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
FoscAcciaccaturaContainer : FoscGraceContainer {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prFormatOpenBracketsSlot
    -------------------------------------------------------------------------------------------------------- */
    prFormatOpenBracketsSlot { |bundle|
        var result;
        result = [];
        result = result.add([['graceBrackets', 'open'], ["\\acciaccatura {"]]);
        ^result;
    }
}
