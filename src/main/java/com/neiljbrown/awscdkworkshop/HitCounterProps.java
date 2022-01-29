package com.neiljbrown.awscdkworkshop;

import software.amazon.awscdk.services.lambda.IFunction;

/**
 * Holds the properties used to create an instance of {@link HitCounter}.
 */
// TODO - This class is currently only used to provide a single property to the HitCounter class. Review whether it's
//  needed. HitCounter could just be changed to accept the IFunction? If it is retained consider simplifying the
//  class. Is the use of the build pattern warranted? And does it need to be an interface? Could refactor to a class
//  with a single arg constructor and getter method.
public interface HitCounterProps {

	/** @return a {@link Builder} for creating an instance of {@link HitCounterProps}. */
	public static Builder builder() {
		return new Builder();
	}

	/** @return the Lambda function which this HitCounter proxies requests for, in order to count the 'hits'. */
	IFunction getDownstream();

	/** Supports building an instance of a {@link HitCounterProps}. */
	public static class Builder {
		private IFunction downstream;

		/**
		 * @param function the {@link IFunction Lambda function} to use to create the HitCounterProps.
		 * @return this builder.
		 */
		public Builder downstream(final IFunction function) {
			this.downstream = function;
			return this;
		}

		/**
		 * Builds the {@link HitCounter} from the state provided in previous builder methods.
		 *
		 * @return the {@link HitCounter} .
		 */
		public HitCounterProps build() {
			if(this.downstream == null) {
				throw new NullPointerException("The downstream property is required!");
			}

			// Create and return and anonymous implementation of HitCounterProps that uses the instance of supplied
			// downstream property
			return new HitCounterProps() {
				public IFunction getDownstream() {
					return downstream;
				}
			};
		}
	}
}