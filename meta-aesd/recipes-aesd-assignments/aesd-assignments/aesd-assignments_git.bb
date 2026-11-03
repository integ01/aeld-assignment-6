# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this  with the path to your assignments repo.  Use https protocol and a public
# repo, or see assignment instructions for use with ssh keys
#SRC_URI = "https://github.com/cu-ecen-aeld/<your repo url>;protocol=https;nobranch=1"
#"git@github.com:cu-ecen-aeld/assignments-3-and-later-integ01.git;protocol=https;nobranch=1"
SRC_URI = "https://github.com/cu-ecen-aeld/assignments-3-and-later-integ01;protocol=https;nobranch=1"



PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
SRCREV = "assignment-6-part1-complete"

# TODO: Add the aesdsocket application and any other files you will install in do_install below
# See https://github.com/openembedded/openembedded-core/blob/wrynose/meta/conf/bitbake.conf for path prefixes like ${bindir}
# or ${sysconfdir}"
FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${sysconfdir}/init.d/aesdsocket-start-stop"

# TODO: customize these as necessary for any libraries you need for your application
TARGET_LDFLAGS += "-pthread -lrt -m"

do_configure () {
	:
}

do_compile () {
    # TODO: switch to the server directory where your source code to be built is located
        oe_runmake -C ${S}/server all
}

#TODO: add initscript necessary changes here
# Init script: replaces Buildroot's S99aesdsocket naming
inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"
INITSCRIPT_PARAMS:${PN} = "defaults 99"


do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See examples at https://github.com/cu-ecen-aeld/yocto-hello-world for your relevant build as well
	# Remember to copy files relative to their location after building, which may be under the server subdirectory

        install -d ${D}${bindir}
        install -m 0755 ${S}/server/aesdsocket ${D}${bindir}/

        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${S}/server/aesdsocket-start-stop.sh ${D}${sysconfdir}/init.d/aesdsocket-start-stop
    }
