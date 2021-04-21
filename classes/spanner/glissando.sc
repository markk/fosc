/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: glissando


SUMMARY:: Returns a glissando.


DESCRIPTION:: TODO


USAGE::

'''

• glissando (abjad 3.0)


• Example 1

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].glissando;
a.show;

img:: ![](../img/spanner-glissando-1.png)
'''

p = "%/fosc/docs/img/spanner-glissando-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

With stems set to true.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].glissando(stems: true);
a.show;

img:: ![](../img/spanner-glissando-2.png)
'''

p = "%/fosc/docs/img/spanner-glissando-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 3

Glissandos can be tweaked.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].glissando(stems: true, tweaks: #[['thickness', 4], ['color', 'red']]);
a.show;

img:: ![](../img/spanner-glissando-3.png)
'''

p = "%/fosc/docs/img/spanner-glissando-3".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
+ FoscSelection {
    glissando { |allowRepeats=false, allowTies=false, parenthesizeRepeats=false, stems=false,
        rightBroken=false, tag, tweaks|

        var leaves, shouldAttachGlissando=false, deactivateGlissando, literal, strings, glissando;

        leaves = this.leaves;

        leaves.do { |leaf|

            if (leaf != this.first) {
                if (parenthesizeRepeats) {
                    if (FoscGlissando.prPreviousLeafChangesCurrentPitch(leaf).not) {
                        FoscGlissando.prParenthesizeLeaf(leaf);
                    };
                };
            };

            case 
            { FoscInspection(leaf).hasIndicator(FoscBendAfter) } {
                // pass
            }
            { leaf == this.last } {
                if (rightBroken) {
                    shouldAttachGlissando = true;
                    tag = true;
                };
            }
            { [FoscChord, FoscNote].every { |type| leaf.isKindOf(type).not } } {
                // pass
            }
            { allowRepeats && allowTies } {
                shouldAttachGlissando = true;
            }
            { allowRepeats && (allowTies.not) } {
                shouldAttachGlissando = FoscGlissando.prIsLastInTieChain(leaf);
            }
            { allowRepeats.not && allowTies } {
                if (FoscGlissando.prNextLeafChangesCurrentPitch(leaf )) {
                    shouldAttachGlissando = true;
                }
            }
            { allowRepeats.not && (allowTies.not) } {
                if (FoscGlissando.prNextLeafChangesCurrentPitch(leaf)) {
                    if (FoscGlissando.prIsLastInTieChain(leaf)) {
                        shouldAttachGlissando = true;
                    };
                };
            };

            if (stems) {
                if (leaf == this[1]) {
                    strings = #[
                        "\\override Accidental.stencil = ##f",
                        "\\override NoteColumn.glissando-skip = ##t",
                        "\\hide NoteHead",
                        "\\override NoteHead.no-ledgers = ##t",
                    ];
                    literal = FoscLilypondLiteral(strings);
                    leaf.attach(literal, tag: tag);
                };
                if (leaf == this.last) {
                    strings = #[
                        "\\revert Accidental.stencil",
                        "\\revert NoteColumn.glissando-skip",
                        "\\undo \\hide NoteHead",
                        "\\revert NoteHead.no-ledgers",
                    ];
                    if (rightBroken) {
                        deactivateGlissando = true;
                        literal = FoscLilypondLiteral(strings, 'after');
                        leaf.attach(literal, deactivate: true);
                        //!!!TODO: leaf.attach(literal, deactivate: true, tag: abjad_tags.SHOW_TO_JOIN_BROKEN_SPANNERS);
                        literal = FoscLilypondLiteral(strings);
                        leaf.attach(literal, deactivate: false);
                        //!!!TODO: leaf.attach(literal, deactivate: true, tag: abjad_tags.HIDE_TO_JOIN_BROKEN_SPANNERS);
                    } {
                        literal = FoscLilypondLiteral(strings);
                        leaf.attach(literal, tag: tag);
                    };
                };
            };

            if (shouldAttachGlissando) {
                glissando = FoscGlissando();
                if (glissando.tweaks.notNil) { tweaks = glissando.tweaks.addAll(tweaks) };
                FoscLilypondTweakManager.setTweaks(glissando, tweaks);
                leaf.attach(glissando, deactivate: deactivateGlissando, tag: tag);
            };
        };
    }
}
