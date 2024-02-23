import cx from "classnames";
import { VISIBILITY_FILTERS } from "utils/constants";

interface TodoFilterProps {
  activeFilter: string;
  setFilter: (current: string) => void;
}

export function Filter({ activeFilter: active, setFilter }: TodoFilterProps) {
  return (
    <div className="visibility-filters">
      {Object.values(VISIBILITY_FILTERS).map(filterKey => {
        return (
          <span
            key={`visibility-filter-${filterKey}`}
            className={cx(
              "filter",
              filterKey === active && "filter--active"
            )}
            onClick={() => {
              setFilter(filterKey);
            }}
          >
            {filterKey}
          </span>
        );
      })}
    </div>
  );
}