/**
 * Abstraction interface and data objects for a logging subsystem. Each logged unit of information is
 * encapsulated in a <em>logging record</em>. The logging record is immutable after creation and contains
 * various metadata about logged information. Some of them are initialized automatically - logging record
 * creator and thread information, others can be specified explicitly. Each logging record has at least
 * one <em>logging tag</em>. The logging tag encapsulates a <em>logging facility</em> and a <em>logging
 * severity</em> for that facility. The important feature is that one logging record may have several
 * logging tags. So, it is possible to specify that a logged information has higher severity for "SECURITY"
 * facility than severity for "PERFORMANCE" facility.
 *
 * @since 2008.11
 */
@Experimental package gems.logging;

import gems.Experimental;
