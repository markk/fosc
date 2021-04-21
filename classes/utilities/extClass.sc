/* ------------------------------------------------------------------------------------------------------------
TITLE:: extClass


SUMMARY:: Returns a extClass.


DESCRIPTION:: TODO


USAGE::

'''

• Class
'''
------------------------------------------------------------------------------------------------------------ */
+ Class {
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isSubclassOf

    code::
    FoscSlur.isSubclassOf(FoscSpanner);
    '''
    -------------------------------------------------------------------------------------------------------- */
    isSubclassOf { |prototype|
        var subclasses;
        subclasses = prototype.subclasses;
        if (subclasses.isNil) { ^false };
        ^(subclasses.includes(this));
    }
}
