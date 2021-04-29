/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscTypedSequenceableCollection


SUMMARY:: Returns a FoscTypedSequenceableCollection.


DESCRIPTION:: TODO


USAGE::

'''

• FoscTypedSequenceableCollection

code::
x = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
x.collection;
x.items;

FoscTypedSequenceableCollection.dumpInterface
'''
------------------------------------------------------------------------------------------------------------ */
FoscTypedSequenceableCollection : FoscTypedCollection {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: COMPARISON
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • ==

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    b = FoscTypedSequenceableCollection([1, 2], Number);
    a == b;
    a == a.copy;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • !=

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    b = FoscTypedSequenceableCollection([1, 2], Number);
    a != b;
    a != a.copy;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: ENUMERATION
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • do

    code::
    x = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    x.do { |each| (each * 2).postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • iter

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a = a.iter;
    5.do { a.next.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • includes

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a.includes(4);
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isCollection

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a.isCollection;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • items

    code::
    x = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    x.items;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • size

    Size of typed sequenceable collection.

    Returns nonnegative integer.

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a.size;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • at

    Gets item at index.

    Returns item.

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a[2];
    '''
    -------------------------------------------------------------------------------------------------------- */
    at { |index|
        if (index.isArray) { ^this.atAll(index) };
        ^collection.at(index);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • atAll

    Gets items at indices.

    Returns items.

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a[(2..3)];
    '''
    -------------------------------------------------------------------------------------------------------- */
    atAll { |indices|
        ^collection.atAll(indices);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • copySeries

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a[1..2];

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a[1..];

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a[..2];

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4, 5, 6, 7], Number);
    a.copySeries(0, 2);
    '''
    -------------------------------------------------------------------------------------------------------- */
    copySeries { |first, second, last|
        ^collection.copySeries(first, second, last);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • includes

    Answer true if item exists in collection.

    Returns boolean.

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a.includes(3);
    a.includes(5);
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • indexOf (abjad: index)

    Return the first index matching item.

    Returns integer.

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a.indexOf(3);
    '''
    -------------------------------------------------------------------------------------------------------- */
    indexOf { |item|
        item = this.prItemCoercer(item);
        ^collection.indexOf(item);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • last

    Return the last item.

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a.last;
    '''
    -------------------------------------------------------------------------------------------------------- */
    last {
        ^collection.last;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • occurrencesOf (abjad: count)

    Return the number of occurrences of item in collection.

    Returns integer.

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a.occurrencesOf(3);
    '''
    -------------------------------------------------------------------------------------------------------- */
    occurrencesOf { |item|
        item = this.prItemCoercer(item);
        ^collection.occurrencesOf(item);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: LIST MODIFICATION
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • pop

    Removes last item.

    code::
    Returns removed item;

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a.pop;
    a.inspect;
    '''
    -------------------------------------------------------------------------------------------------------- */
    pop {
        var oldItem;
        oldItem = collection.pop;
        this.prOnRemoval(oldItem);
        ^oldItem;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • remove

    Remove item.

    Returns removed item.

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a.remove(2);
    a.items;
    '''
    -------------------------------------------------------------------------------------------------------- */
    remove { |item|
        item = this.prItemCoercer(item);
        this.prOnRemoval(item);
        ^collection.remove(item);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • removeAt

    Remove item at index.

    Returns removed item.

    code::
    a = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    a.removeAt(1);
    a.items;
    '''
    -------------------------------------------------------------------------------------------------------- */
    removeAt { |index|
        ^collection.removeAt(index);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: TRANSFORMATION
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • collect

    code::
    x = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    x = x.collect { |each| each * 2 };
    x.inspect;
    '''
    -------------------------------------------------------------------------------------------------------- */
    collect { |func|
        var items;
        items = this.items.collect(func);
        ^this.species.new(items, itemClass);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • reject

    code::
    x = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    x = x.reject { |each| each.even };
    x.inspect;
    '''
    -------------------------------------------------------------------------------------------------------- */
    reject { |func|
        var items;
        items = this.items.reject(func);
        ^this.species.new(items, itemClass);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • select

    code::
    x = FoscTypedSequenceableCollection([1, 2, 3, 4], Number);
    x = x.select { |each| each.even };
    x.inspect;
    '''
    -------------------------------------------------------------------------------------------------------- */
    select { |func|
        var items;
        items = this.items.select(func);
        ^this.species.new(items, itemClass);
    }
}
