# Copyright 2020 The Bazel Authors. All rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

_KOPTS = {
    "warn": struct(
        args = dict(
            default = True,
            doc = "Enable or disable compiler warnings.",
        ),
        type = attr.bool,
    ),
    "x_use_experimental": struct(
        args = dict(
            default = True,
            doc = "Allow the experimental language features.",
        ),
        type = attr.bool,
    ),
}

KotlincOptions = provider(
    fields = {
        name: o.args["doc"]
        for name, o in _KOPTS.items()
    },
)

def _kotlinc_options_impl(ctx):
    return struct(
        providers = [
            KotlincOptions(**{n: getattr(ctx, n, None) for n in _KOPTS}),
        ],
    )

kt_kotlinc_options = rule(
    implementation = _kotlinc_options_impl,
    doc = "Define kotlin compiler options.",
    provides = [KotlincOptions],
    attrs = {
        n: o.type(**o.args)
        for n, o in _KOPTS.items()
    },
)

_JOPTS = {
    "warn": struct(
        args = dict(
            default = True,
            doc = "Enable or disable compiler warnings.",
        ),
        type = attr.bool,
    ),
    "x_ep_disable_all_checks": struct(
        args = dict(
            default = False,
            doc = "See javac -XepDisableAllChecks documentation",
        ),
        type = attr.bool,
    ),
    "x_lint": struct(
        args = dict(
            default = [],
            doc = "See javac -Xlint: documentation",
        ),
        type = attr.string_list,
    ),
    "xd_suppress_notes": struct(
        args = dict(
            default = False,
            doc = "See javac -XDsuppressNotes documentation",
        ),
        type = attr.bool,
    ),
}

JavacOptions = provider(
    fields = {
        name: o.args["doc"]
        for name, o in _JOPTS.items()
    },
)

def _javac_options_impl(ctx):
    return struct(
        providers = [
            JavacOptions(**{n: getattr(ctx, n, None) for n in _JOPTS}),
        ],
    )

kt_javac_options = rule(
    implementation = _javac_options_impl,
    doc = "Define java compiler options for kt_jvm_* rules with java sources.",
    provides = [JavacOptions],
    attrs = {n: o.type(**o.args) for n, o in _JOPTS.items()},
)
