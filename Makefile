path := $(abspath $(lastword $(MAKEFILE_LIST)))
dir := $(dir $(path))
source := classes
markdown := docs
www := www

rwildcard = $(foreach d,$(wildcard $1*),$(call rwildcard,$d/,$2) $(filter $(subst *,%,$2),$d))

sources := $(call rwildcard,$(source),*.sc)
markdowns := $(patsubst %.sc,%.md,$(subst $(source),$(markdown),$(sources)))
htmls := $(patsubst %.sc,%.html,$(subst $(source),$(www),$(sources)))

doc: $(markdowns)

html: $(htmls)

$(markdown)/%.md: $(source)/%.sc
	mkdir -p $(@D)
	mkdir -p $(dir)docs/img
	printf "FoscMarkdownFile(\"%s%s\").write(\"%s%s\");" $(dir) $< $(dir) $@ | sclang

$(www)/%.html: $(markdown)/%.md
	mkdir -p $(@D)
	ln -sft $(dir)www ../docs/img
	pandoc --quiet -s --syntax-definition=supercollider.xml -o $@ --css /github-pandoc.css --css /style.css $<

.PRECIOUS: $(markdown)/%.md

.PHONY: clean
clean:
	-rm -rf $(dir)docs/*
	-rm -f $(dir)www/img
	-find $(dir)www/ -type f -name "*.html" -delete
	-find $(dir)www/ -mindepth 1 -type d -delete
