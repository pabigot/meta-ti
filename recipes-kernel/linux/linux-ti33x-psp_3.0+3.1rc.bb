SECTION = "kernel"
DESCRIPTION = "Linux kernel for TI33x EVM from PSP, based on am335x-kernel"
LICENSE = "GPLv2"
KERNEL_IMAGETYPE = "uImage"

require multi-kernel.inc

S = "${WORKDIR}/git"

MULTI_CONFIG_BASE_SUFFIX = ""

BRANCH = "master"
SRCREV = "10a9c95f6713ce4f7db7088443676808ca79a851"
MACHINE_KERNEL_PR_append = "c+gitr${SRCREV}"

COMPATIBLE_MACHINE = "(ti33x)"

THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
CONFIGS_PSP = "${@base_set_filespath(["${THISDIR}/${PN}-${PV}/tipspkernel"], d)}:\
${@base_set_filespath(["${THISDIR}/${PN}/tipspkernel"], d)}:\
${@base_set_filespath(["${THISDIR}/files/tipspkernel"], d)}:"
FILESPATH =. "${@base_contains('DISTRO_FEATURES', 'tipspkernel', "${CONFIGS_PSP}", "", d)}"

SRC_URI += "git://arago-project.org/git/projects/linux-am33x.git;protocol=git;branch=${BRANCH} \
	file://defconfig"

PATCHES_OVER_PSP = " \
	file://0001-f_rndis-HACK-around-undefined-variables.patch \
	file://0001-am335x-evm-hack-in-LED-support-for-beaglebone.patch \
	file://0001-mach-types-Add-new-machine-type-beaglebone-to-mach-t.patch \
	file://0002-Kconfig-Add-support-for-beaglebone-machine-id.patch \
	file://0003-am335xevm-Use-new-beaglebone-machine-id.patch \
	"

SRC_URI += "${@base_contains('DISTRO_FEATURES', 'tipspkernel', "", "${PATCHES_OVER_PSP}", d)}"