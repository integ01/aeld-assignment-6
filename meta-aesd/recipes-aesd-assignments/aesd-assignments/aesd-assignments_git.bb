# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this  with the path to your assignments repo.  Use https protocol and a public
# repo, or see assignment instructions for use with ssh keys
#SRC_URI = "https://github.com/cu-ecen-aeld/<your repo url>;protocol=https;nobranch=1"
#"git@github.com:cu-ecen-aeld/assignments-3-and-later-integ01.git;protocol=https;nobranch=1"
SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-integ01;protocol=https;nobranch=1"
SRC_URI[sha256sum] = "4dba36dc72296105c8d91c908b6e8efed85b934028c7424bfdfec4012141239e"

PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
#SRCREV = "assignment-6-part1-complete"
#SRCREV = "09577a535267f30575835ce5b8ceec11dbf030f5"
SRCREV = "55ff468a594dd5d224db4809bff21f5e90ff379b"
# TODO: Add the aesdsocket application and any other files you will install in do_install below
# See https://github.com/openembedded/openembedded-core/blob/wrynose/meta/conf/bitbake.conf for path prefixes like ${bindir}
# or ${sysconfdir}"
FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${sysconfdir}/init.d/aesdsocket-start-stop"

# TODO: customize these as necessary for any libraries you need for your application
TARGET_LDFLAGS += "-pthread -lrt -lm"

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
        install -d ${D}${bindir}
        install -m 0755 ${S}/server/aesdsocket ${D}${bindir}/
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${S}/server/aesdsocket-start-stop.sh ${D}${sysconfdir}/init.d/aesdsocket-start-stop
}

