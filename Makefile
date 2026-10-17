GRADLE=./gradlew

.PHONY: all build test coverage run clean

all: build

build:
	$(GRADLE) build

test:
	$(GRADLE) test

coverage:
	$(GRADLE) jacocoTestReport

run:
	$(GRADLE) run

clean:
	$(GRADLE) clean

clean-coverage:
	rm -rf build/customJacocoReportDir build/jacocoHtml
