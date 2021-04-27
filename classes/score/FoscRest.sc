/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscRest


SUMMARY:: Returns a FoscRest.


DESCRIPTION:: TODO


USAGE::

'''

• FoscRest


code::
a = FoscRest(3/16);
a.show;

img:: ![](../img/score-rest-1.png)
'''

p = "%/fosc/docs/img/score-rest-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));




• implicit conversion of type when another leaf is passed as initialization argument; indicators are preserved

code::
n = FoscNote(60, 3/16);
n.attach(FoscArticulation('>'));
a = FoscRest(n);
a.show;

img:: ![](../img/score-rest-2.png)
'''

p = "%/fosc/docs/img/score-rest-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
FoscRest : FoscLeaf {
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// INIT
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	*new { |writtenDuration, multiplier, tag|
        var originalArgument, new;
        originalArgument = writtenDuration;
        if (originalArgument.isKindOf(FoscLeaf)) {
            writtenDuration = originalArgument.writtenDuration;
            multiplier = originalArgument.multiplier;
        };
        writtenDuration = writtenDuration ?? { FoscDuration(1, 4) };
        new = super.new(writtenDuration, multiplier, tag);
        if (originalArgument.isKindOf(FoscLeaf)) {
            new.prCopyOverrideAndSetFromLeaf(originalArgument);
        };
        ^new;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC INSTANCE METHODS: Special Methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* --------------------------------------------------------------------------------------------------------
    '''
    • asCompileString

    code::
    a = FoscRest(1/4);
    a.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
        ^"%(%)".format(this.species, writtenDuration.str);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetBody

    code::
    a = FoscRest(1/4);
    a.prGetBody;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetBody {
    	^[this.prGetCompactRepresentation];
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetCompactRepresentation

    code::
    a = FoscRest(1/4);
    a.prGetCompactRepresentation;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetCompactRepresentation {
    	^"r%".format(this.prGetFormattedDuration)
    }
}
