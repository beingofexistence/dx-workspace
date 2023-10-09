import React from 'react';
import { SVGIcon, ISVGIconProps } from './SVGIcon';
import { useUniqueId } from './useUniqueId';

export const QuasarIcon: React.FC<ISVGIconProps> = ({ ...props }) => {
  const id = useUniqueId();

  return (
    <SVGIcon {...props} viewBox={"0 0 1024 1024"}>
      <g clipPath={`url(#Quasar_Clip0_${id})`}>
        <path
          d="M586.2 512a74.2 74.2 0 01-148.4 0 74.2 74.2 0 11148.4 0z"
          fill="#fff"
        />
        <path
          d="M841.3 321.8a378.5 378.5 0 00-58.3-76.2l-85.7 49.5a286 286 0 00-89.6-51.9 348.6 348.6 0 00-69.3 98.9c95.5-6.5 194.1 28 285.6 99.6l53.9-31.1c-8.3-31-20.7-60.9-36.6-88.8z"
          fill="#fff"
        />
        <path
          d="M512 892.3c32.1-.1 64-4.2 95.2-12.4v-99a284 284 0 0089.7-51.6 352.3 352.3 0 00-51-109.5c-42.1 86-121.3 154-229 197.6v62.3a382 382 0 0095.1 12.6z"
          fill="#fff"
        />
        <path
          d="M182.7 321.9a373.6 373.6 0 00-36.8 88.6l85.7 49.5a285.2 285.2 0 00-.2 103.4A350 350 0 00351.7 574c-53.4-79.4-72.8-182.1-56.6-297.1l-53.9-31.1a376.9 376.9 0 00-58.5 76.1z"
          fill="#fff"
        />
        <g>
          <path
            d="M841.3 702.1c16-27.8 28.4-57.6 36.8-88.6L792.5 564c6.4-34.5 6.4-69.6.2-103.4-40.3-10.6-80.6-14.1-120.3-10.6 53.4 79.4 72.8 182.1 56.6 297.1l53.9 31.1a375.6 375.6 0 0058.4-76.1z"
            fill="#00b4ff"
          />
          <path
            d="M182.7 702.1a378.5 378.5 0 0058.3 76.2l85.7-49.5a286 286 0 0089.6 51.9 348.6 348.6 0 0069.3-98.9c-95.5 6.5-194.1-28-285.6-99.6l-54 31.1c8.4 31.1 20.8 61 36.7 88.8z"
            fill="#00b4ff"
          />
          <path
            d="M512 131.7c-32.1.1-64 4.2-95.2 12.4v99a284 284 0 00-89.7 51.6c11 40.2 28.2 76.9 51 109.5 42.1-86 121.3-154 229-197.6v-62.3c-30.9-8.2-63-12.4-95.1-12.6z"
            fill="#00b4ff"
          />
        </g>
        <path
          d="M512 66.4c245.7 0 445.6 199.9 445.6 445.6S757.7 957.6 512 957.6 66.4 757.7 66.4 512 266.3 66.4 512 66.4M512 1a511 511 0 100 1022A511 511 0 00512 1z"
          fill="#00b4ff"
        />
      </g>
      <defs>
        <clipPath id={`Quasar_Clip0_${id}`}>
          <rect width="1024" height="1024" fill="white" />
        </clipPath>
      </defs>
    </SVGIcon>
  );
};