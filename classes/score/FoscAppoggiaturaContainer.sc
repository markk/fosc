/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscAppoggiaturaContainer


SUMMARY:: Returns a FoscAppoggiaturaContainer.


DESCRIPTION:: TODO


USAGE::

'''

• FoscAppoggiaturaContainer


Appoggiatura container.

Appoggiaturas are played on the beat.

LilyPond positions appoggiaturas immediately before main notes.

LilyPond formats appoggiaturas with a slur but without a slashed stem.


• Example 1

Appoggiatura notes.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
c = FoscAppoggiaturaContainer([FoscNote(60, 1/16), FoscNote(62, 1/16)]);
c[0].attach(FoscArticulation('>'));
a[2].attach(c);
a.format;
a.show;

img:: ![](../img/score-appoggiatura-container-1.png)
'''

p = "%/fosc/docs/img/score-appoggiatura-container-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
FoscAppoggiaturaContainer : FoscGraceContainer {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prFormatOpenBracketsSlot
    '''
    -------------------------------------------------------------------------------------------------------- */
    prFormatOpenBracketsSlot { |bundle|
        var result;
        result = [];
        result = result.add([['graceBrackets', 'open'], ["\\appoggiatura {"]]);
        ^result;
    }
}
