/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscTypedSet


SUMMARY:: Returns a FoscTypedSet.


DESCRIPTION:: TODO


USAGE::

'''

• FoscTypedSet

code::
x = FoscTypedSet([1, 1, 2, 3, 4], Number);
x.inspect;
'''
------------------------------------------------------------------------------------------------------------ */
FoscTypedSet : FoscTypedCollection {	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	init { |items, argItemClass|
        var set;
        super.init(items, argItemClass);
        set = [];
        collection = collection.collect { |item| this.prItemCoercer(item) };
        collection.do { |item| if (set.includesEqual(item).not) { set = set.add(item) } };
        collection = set;
	}
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • difference

    Set-theoretic difference of receiver and expr.

    Returns new typed frozen set.

    code::
    a = FoscTypedSet([1, 2, 3], Number);
    b = FoscTypedSet([2, 3, 4], Number);
    x = a.difference(b); // x = (a - b);
    x.inspect;

    code::
    a = FoscPitchClassSet([1, 2, 3]);
    b = FoscPitchClassSet([2, 3, 4]);
    a.difference(b).do { |each| each.pitchClassNumber.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • sect

    Set-theoretic intersection of receiver and expr.

    Returns new typed frozen set.

    code::
    a = FoscTypedSet([1, 2, 3], Number);
    b = FoscTypedSet([2, 3, 4], Number);
    x = sect(a, b); // x = (a & b);
    x.inspect;

    code::
    a = FoscPitchClassSet([1, 2, 3]);
    b = FoscPitchClassSet([2, 3, 4]);
    a.sect(b).do { |each| each.pitchClassNumber.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isDisjoint

    Is true when typed receiver shares no elements with expr. Otherwise false.

    Returns boolean.

    code::
    a = FoscTypedSet([1, 2, 3], Number);
    b = FoscTypedSet([4], Number);
    c = FoscTypedSet([3, 4], Number);
    isDisjoint(a, b);
    isDisjoint(a, c);
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isEmpty

    Is true when set is empty.

    Returns boolean.

    code::
    a = FoscTypedSet([1, 2, 3], Number);
    a.isEmpty;

    code::
    a = FoscTypedSet([], Number);
    a.isEmpty;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isSubsetOf

    Is true when receiver is a subset of expr. Otherwise false.

    Returns boolean.


    code::
    a = FoscTypedSet([1, 2, 3], Number);
    b = FoscTypedSet([4], Number);
    c = FoscTypedSet([1, 2, 3, 4], Number);
    b.isSubsetOf(a);
    a.isSubsetOf(c);

    code::
    a = FoscPitchClassSet([1, 2, 3]);
    b = FoscPitchClassSet([2, 3]);
    a.isSubsetOf(b);
    b.isSubsetOf(a);
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isSupersetOf

    Is true when receiver is a superset of expr. Otherwise false.

    Returns boolean.

    code::
    a = FoscTypedSet([1, 2, 3], Number);
    b = FoscTypedSet([4], Number);
    c = FoscTypedSet([1, 2, 3, 4], Number);
    b.isSupersetOf(a);
    c.isSupersetOf(a);

    code::
    a = FoscPitchClassSet([1, 2, 3]);
    b = FoscPitchClassSet([2, 3]);
    a.isSupersetOf(b);
    b.isSupersetOf(a);
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • notEmpty

    Is true when set is not empty.

    Returns boolean.

    code::
    a = FoscTypedSet([1, 2, 3], Number);
    a.notEmpty;

    code::
    a = FoscTypedSet([], Number);
    a.notEmpty;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • symmetricDifference

    Symmetric difference of receiver and expr.

    Returns new typed frozen set.

    code::
    a = FoscTypedSet([1, 2, 3], Number);
    b = FoscTypedSet([2, 3, 4], Number);
    x = symmetricDifference(a, b); // a -- b;
    x.inspect;

    code::
    a = FoscPitchClassSet([1, 2, 3]);
    b = FoscPitchClassSet([2, 3, 4]);
    a.symmetricDifference(b).do { |each| each.pitchClassNumber.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • union

    Union of receiver and expr.

    Returns new typed frozen set.

    code::
    a = FoscTypedSet([1, 2, 3], Number);
    b = FoscTypedSet([2, 3, 4], Number);
    x = union(a, b); // x = (a | b);
    x.inspect;

    code::
    a = FoscPitchClassSet([1, 2, 3]);
    b = FoscPitchClassSet([2, 3, 4]);
    a.union(b).do { |each| each.pitchClassNumber.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
}
